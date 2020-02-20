package com.wanghao.cms.kafka;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.wanghao.cms.dao.ArticleMapper;
import com.wanghao.cms.dao.ArticleRep;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.service.ArticleService;
//运行时自动后台运行
public class MsgListener implements MessageListener<String, String> {

	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	ArticleRep articleRep;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		System.err.println("kafka接受消息");
		//接受消息
		String value = data.value();
		if(value.substring(0, 3).equals("add")) {
			
			//把json类型的字符串转 换成article对象
			Article article = JSON.parseObject(value.substring(3), Article.class);
			System.out.println(article);
			System.err.println("以上是接收到kafka接受消息___添加或修改操作");
			//添加到es索引库 审核通过的时候执行两次
			articleRep.save(article);
		}
		if(value.substring(0, 3).equals("inf")) {
			
			///把json类型的字符串转换成article对象
			Article article = JSON.parseObject(value.substring(3), Article.class);
			System.err.println("点击数加1");
			int hits = article.getHits();
			hits++;
			articleMapper.hitsAdd(article.getId(), hits);
		}
		if(value.substring(0, 3).equals("del")) {
			
			//把json类型的字符串转换成article对象
			Article article = JSON.parseObject(value.substring(3), Article.class);
			System.out.println(article);
			System.err.println("以上是接收到kafka接受消息____删除操作");
			//添加到es索引库 审核通过的时候执行两次
			articleRep.delete(article);
		}
		if(value.substring(0, 3).equals("one")){
			//把json类型的字符串转换成article对象
			Article article = JSON.parseObject(value.substring(3), Article.class);
			//添加到redis随后就删除
			redisTemplate.opsForValue().set(article.getTitle(), article, 1, TimeUnit.SECONDS);
			//添加到数据库
			articleMapper.add(article);
			System.err.println("${"+article.getTitle()+"}已导入完毕");
		}
		
		
		
	}

}
