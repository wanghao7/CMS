package com.wanghao.cms.service;

import java.util.List;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Category;
import com.wanghao.cms.entity.Channel;
import com.wanghao.cms.entity.Comment;
import com.wanghao.cms.entity.Complain;
import com.wanghao.cms.entity.Link;
import com.wanghao.cms.entity.Slide;

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

	PageInfo<Article> hotList(int page);

	List<Article> lastLiat();

	List<Slide> getSlides();
	/**
	 * 获取栏目下的文章
	 * @param channleId
	 * @param catId
	 * @param page
	 * @return
	 */
	PageInfo<Article> getArticles(int channelId, int catId, int page);

	List<Category> getCategoriesByChannelId(int channelId);

	int addComment(Comment comment);

	PageInfo<Comment> getComments(int id, int page);

	PageInfo<Link> link(int page);

	int addComplian(@Valid Complain complain);

	PageInfo<Complain> getComplains(int articleId, int page);

}
