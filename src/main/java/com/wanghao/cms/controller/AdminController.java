package com.wanghao.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsError;
import com.wanghao.cms.common.CmsMessage;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Link;
import com.wanghao.cms.service.ArticleService;
@RequestMapping("admin")
@Controller
public class AdminController {

	@Autowired
	private ArticleService articleService;
	
	
	
	@RequestMapping("index")
	public String index() {
		return "admin/index";
	}
	/**
	 * 管理员文章管理
	 * @param status
	 * @param page
	 * @param m
	 * @return
	 */
	@RequestMapping("article")
	public String article(HttpServletRequest request,int status,@RequestParam(defaultValue= "1") int page) {
		PageInfo<Article> articlePage =  articleService.List(status ,page);
		request.setAttribute("status", status);
		request.setAttribute("articlePage", articlePage);
		return "/admin/article/list";
		
	}
//	@RequestMapping("articles")
//	public String articlesList(int status,int page,Model m ) {
//		PageHelper.startPage(page, 5);
//		List<Article> list = articleService.getList(status);
//		PageInfo<Article> articlePage = new PageInfo<>(list);
//		PageInfo<Article> articlePage = articleService.List(status,page);
//		
//		m.addAttribute("articlePage", articlePage);
//		return "admin/article/list";
//	}
	
	/**
	 * 改变文章状态
	 * @param id
	 * @param status
	 * @return
	 * setArticleStatus
	 */
	@RequestMapping("setArticleStatus")
	@ResponseBody
	public CmsMessage  setArticeStatus(int id,int status) {
		
		/**
		 * 数据合法性校验 
		 */
		if(status !=1 && status!=2) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"status参数值不合法",null);
		}
		
		if(id<0) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		
		Article article = articleService.getInfoById(id);
		if(article==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"数据不存在",null);
		}
		
		/**
		 * 
		 */
		if(article.getStatus()==status) {
			return new CmsMessage(CmsError.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		/**
		 *  修改数据
		 */
		int result = articleService.setCheckStatus(id,status);
		//往es里面添加修改后的数据
		articleService.getInfoById(id);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
		
	}
	/**
	 * 设置热门
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("setArticeHot")
	@ResponseBody
	public CmsMessage setArticeHot(int id,int status) {
		/**
		 * 数据合法性校验 
		 */
		if(status !=0 && status!=1) {
			
		}
		
		if(id<0) {
			
		}
		
		Article article = articleService.getInfoById(id);
		if(article==null) {
			
		}
		if(article.getStatus()==status) {
			
		}
		int result = articleService.setHot(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
		
	}
	/**
	 * 友情链接
	 * @return
	 */
	@RequestMapping("link")
	public String link(Model m,@RequestParam(defaultValue="1") int page) {
		
		PageInfo<Link> pageInfo = articleService.link(page);
		m.addAttribute("pageInfo", pageInfo);
		
		return "admin/link/list";
	}
	
}
