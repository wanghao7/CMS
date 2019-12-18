package com.wanghao.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Category;
import com.wanghao.cms.entity.Channel;

public interface ArticleService {

	PageInfo<Article> listByUser(int id, int pageNum);

	int delete(Integer id);

	List<Channel> getChannels();

	List<Category> getCategorisByCid(int cid);

	int add(Article article);

	Article getById(int id);

	int update(Article article, int id);

	PageInfo<Article> List(int status, int page);

	Article getInfoById(int id);

	int setCheckStatus(int id, int status);

	int setHot(int id, int status);

}
