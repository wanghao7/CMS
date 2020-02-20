package com.wanghao.cms.dao;


import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wanghao.cms.entity.Article;

public interface ArticleRep  extends ElasticsearchRepository<Article, Integer> {



	List<Article> findByTitle(String key);

	

}
