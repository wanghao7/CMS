//package com.wanghao.cms.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import com.wanghao.cms.entity.Article;
//
//public class RedisArticleService  {
//
//	@Autowired
//	KafkaTemplate<String,String> kafkaTemplate ;
//	
//	@Autowired
//	RedisTemplate redisTemplate;
//	
//	static int i =0;
//
//	public int addImpl(Article article) {
//		redisTemplate.opsForValue().set(++i, article+"");
//		System.err.println(++i);
//		//将对象转换成string类型
////		String jsonString = JSON.toJSONString(article);
//		//发送给kafka  articles
////		kafkaTemplate.send("articles", "one"+jsonString);
//		
//		return 0;
//	}
//
//}
