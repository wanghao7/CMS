package com.wanghao.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanghao.cms.common.CmsError;
import com.wanghao.cms.common.CmsMessage;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.service.ArticleService;

import sun.java2d.cmm.CMSManager;

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
}
