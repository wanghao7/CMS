package com.wanghao.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.common.CmsError;
import com.wanghao.cms.common.CmsMessage;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Comment;
import com.wanghao.cms.entity.User;
import com.wanghao.cms.service.ArticleService;


@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@RequestMapping("getDetail")
	@ResponseBody
	public CmsMessage getDetail(Integer id) {
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
}
