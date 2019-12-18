package com.wanghao.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.dao.ArticleMapper;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Category;
import com.wanghao.cms.entity.Channel;
import com.wanghao.cms.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper articleMapper;

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
}
