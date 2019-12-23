package com.wanghao.cms.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanghao.cms.dao.SlideMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.dao.ArticleMapper;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Category;
import com.wanghao.cms.entity.Channel;
import com.wanghao.cms.entity.Comment;
import com.wanghao.cms.entity.Complain;
import com.wanghao.cms.entity.Link;
import com.wanghao.cms.entity.Slide;
import com.wanghao.cms.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper articleMapper;

	@Autowired
	SlideMapper slideMapper;
	
	@Override
	public PageInfo<Article> listByUser(int id, int pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		PageInfo<Article> articlePage = new PageInfo<Article>(articleMapper.listByUser(id));
		return articlePage;
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.delete(id);
	}

	@Override
	public List<Channel> getChannels() {
		// TODO Auto-generated method stub
		return articleMapper.getChannels();
	}

	@Override
	public List<Category> getCategorisByCid(int cid) {
		// TODO Auto-generated method stub
		return articleMapper.getCategorisByCid(cid);
	}

	@Override
	public int add(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.add(article);
	}

	@Override
	public Article getById(int id) {
		// TODO Auto-generated method stub
		return articleMapper.getById(id);
	}

	@Override
	public int update(Article article, int id) {
		Article articleSrc = this.getById(article.getId());
		if(articleSrc.getUserId() != id) {
			// todo 。
		}
		return articleMapper.update(article);
	}

	@Override
	public PageInfo<Article> List(int status, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		return new PageInfo<Article>(articleMapper.list(status));
	}

	@Override
	public Article getInfoById(int id) {
		// TODO Auto-generated method stub
		return articleMapper.getInfoById(id);
	}

	@Override
	public int setCheckStatus(int id, int status) {
		// TODO Auto-generated method stub
		return articleMapper.CheckStatus(id,status);
	}

	@Override
	public int setHot(int id, int status) {
		// TODO Auto-generated method stub
		return articleMapper.setHot(id,status);
	}

	@Override
	public PageInfo<Article> hotList(int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		return new PageInfo<>(articleMapper.hostList());
	}

	@Override
	public List<Article> lastLiat() {
		// TODO Auto-generated method stub
		
		return articleMapper.lastList(CmsContant.PAGE_SIZE);
	}

	@Override
	public List<Slide> getSlides() {
		// TODO Auto-generated method stub
		return slideMapper.getSlides();
	}

	@Override
	public PageInfo<Article> getArticles(int channelId, int catId, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		return  new PageInfo<Article>(articleMapper.getArticles(channelId,catId)) ;
	}

	@Override
	public java.util.List<Category> getCategoriesByChannelId(int channelId) {
		// TODO Auto-generated method stub
		return articleMapper.getCategoriesByChannelId(channelId);
	}

	@Override
	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
		int result = articleMapper.addComment(comment);
		if(result>0) {
			articleMapper.increaseCommentCount(comment.getUserId());
		}
		return result;
	}

	@Override
	public PageInfo<Comment> getComments(int articleId, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		return new PageInfo<Comment>(articleMapper.getComments(articleId));
	}

	@Override
	public PageInfo<Link> link(int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		return new PageInfo<Link>(articleMapper.getLink());
	}

	@Override
	public int addComplian(@Valid Complain complain) {
		//添加投诉到数据库
		int result = articleMapper.addCoplain(complain);
		// 增加投诉的数量
		if(result>0)
			articleMapper.increaseComplainCnt(complain.getArticleId());
		
		return 0;
	}

	@Override
	public PageInfo<Complain> getComplains(int articleId, int page) {
		
		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		return new PageInfo<Complain>(articleMapper.getComplains(articleId));
	}
	
	
}
