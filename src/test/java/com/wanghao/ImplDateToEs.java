package com.wanghao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wanghao.cms.dao.ArticleMapper;
import com.wanghao.cms.dao.ArticleRep;
import com.wanghao.cms.entity.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ImplDateToEs {

	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ArticleRep articleRep ;
	
	@Test
	public void im() {
		//在Kafka消费者，将数据存入mysql数据中之后，将数据存入ElasticSearch中。
		List<Article> list = articleMapper.findAllArticleWithStatus(0);
		articleRep.saveAll(list);
	}
	@Test
	public void mech() {
		String reg = "123456";
		System.out.println(reg.substring(0,3));
	}
}
