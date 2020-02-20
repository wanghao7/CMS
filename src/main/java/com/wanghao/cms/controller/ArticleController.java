package com.wanghao.cms.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wanghao.cms.entity.Complain;
import com.wanghao.cms.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.common.CmsError;
import com.wanghao.cms.common.CmsMessage;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Bookmark;
import com.wanghao.cms.entity.Comment;
import com.wanghao.cms.entity.User;
import com.wanghao.cms.service.ArticleService;
import com.wanghao.cms.utils.StringUtils;


@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{

	@Autowired
	ArticleService articleService;
	
	//线程池注入 线程池任务执行   executor对象要与配置文件一致
	@Autowired
	ThreadPoolTaskExecutor executor;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	static int id;
	@RequestMapping("getDetail")
	@ResponseBody
	public CmsMessage getDetail(Integer id,HttpServletRequest request) {
		if(id<=0) {
			
		}
		//获取文章详情
		Article article = articleService.getById(id);
		
		System.out.println(article);
		if(article==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"文章不存在",null);
		}
		//返回数据
		return new CmsMessage(CmsError.SUCCESS,"",article);
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id) {

		Article article = articleService.getById(id);
		String userId = request.getRemoteAddr();//用户id
		String Key = "Hits"+id+userId;
		String RedisArticle = (String) redisTemplate.opsForValue().get(Key);
		
		if(RedisArticle==null) {
			//Sping线程池
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					
					article.setHits(article.getHits()+1);
					//更新到数据库
					articleService.update(article, article.getId()+1);
					//保存redis,value值为空,有效时长为5分钟
					//目的:一个用户在5分钟之内,只记录一次浏览量
					redisTemplate.opsForValue().set(Key, "", 5, TimeUnit.MINUTES);
					
				}
			});
		}
		
		request.setAttribute("article", article);
		return "detail";
	}
	/**
	 * 添加评论
	 * @return
	 */
	@RequestMapping("postComment")
	@ResponseBody
	public CmsMessage postComment(HttpServletRequest request,int articleId,String content) {
		
		User user = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		if(user==null) {
			//信息的构造函数 第一个是常量 1表示成功  
//			第二个是 自定义信息
//			第三是  成功后返回的对象 
			return new CmsMessage(CmsError.NOT_LOGIN,"您尚未登录",null);
		}
		//评论的构造函数
		Comment comment = new Comment();
		comment.setUserId(user.getId());
		comment.setContent(content);
		comment.setArticleId(articleId);
		int result = articleService.addComment(comment);
		if(result>0) {
			return new CmsMessage(CmsError.SUCCESS, "成功", null);
		}
		return new CmsMessage(CmsError.FAILED_UPDATE_DB, "异常原因失败，请与管理员联系", null);
	}
	//获取所有评论
	@RequestMapping("comments")
	public String comments(HttpServletRequest request,int id,int page) {
		PageInfo<Comment> commentPage = articleService.getComments(id,page);
		request.setAttribute("commentPage", commentPage);
		return "comments";
	}
	
	/**
	 * 跳转到投诉的页面
	 * @param request
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="complain",method=RequestMethod.GET)
	public String complain(HttpServletRequest request,int articleId) {
		Article article= articleService.getById(articleId);
		id=articleId;
		request.setAttribute("id", id);
		request.setAttribute("article", article);
		request.setAttribute("complain", new Complain());
		return "article/complain";
				
	}
	/**
	 * 跳转到收藏的页面
	 * @param request
	 * @param articleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Bookmarks")
	public Object Bookmarks(HttpServletRequest request,String articleId,String url) {
		System.err.println(articleId);
		System.out.println(url);
		User loginUser  =  (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		System.out.println(loginUser);
		if(loginUser!=null) {
			if(!StringUtils.isHttpUrl(url)) {
				Article article= articleService.getById(Integer.parseInt(articleId));
				Bookmark bookmark = new Bookmark(null, article.getTitle(), url, loginUser.getId()+"", "");
				int i = articleService.addBook(bookmark);
				if(i>0) {
					return true;
				}else {
					return false;
				}
			}
			return "";
		}else{
			return "err";
			
		}
	}
	
	
	
	/**
	 * 接受投诉页面提交的数据
	 * @param request
	 * @param articleId
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="complain",method=RequestMethod.POST)
	public String complain(HttpServletRequest request,
			@ModelAttribute("complain") @Valid Complain complain,
			MultipartFile file,
			BindingResult result) throws IllegalStateException, IOException {
		
		if(!StringUtils.isHttpUrl(complain.getSrcUrl())) {
			result.rejectValue("srcUrl", "", "不是合法的url地址");
		}
		if(result.hasErrors()) {
			request.setAttribute("id", id);
			return "article/complain";
		}
		
		User loginUser  =  (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		
		String picUrl = this.processFile(file);
		complain.setPicture(picUrl);
		
		
		//加上投诉人
		if(loginUser!=null)
			complain.setUserId(loginUser.getId());
		else
			complain.setUserId(0);
		
		articleService.addComplian(complain);
		
		return "redirect:/article/detail?id="+complain.getArticleId();
				
	}
	/**
	 * 投诉
	 * @param request
	 * @param articleId
	 * @param page
	 * @return
	 */
	@RequestMapping("complains")
	public String 	complains(HttpServletRequest request,int articleId,
			@RequestParam(defaultValue="1") int page) {
		PageInfo<Complain> complianPage=   articleService.getComplains(articleId, page);
		request.setAttribute("complianPage", complianPage);
		return "article/complainslist";
	}
	/**
	 * 投诉列表2
	 * @param request
	 * @param articleId
	 * @param page
	 * @return
	 */
	@RequestMapping("complains2")
	public String 	complains2(HttpServletRequest request,
			@RequestParam(defaultValue="1") int page) {
		PageInfo<Complain> complianPage2=   articleService.getComplains2( page);
		request.setAttribute("complianPage2", complianPage2);
		return "article/complainslist2";
	}
}
