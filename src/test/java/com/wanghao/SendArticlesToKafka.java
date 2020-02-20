package com.wanghao;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.util.FileUtilIO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:producer.xml")
public class SendArticlesToKafka {

	@Autowired
	KafkaTemplate<String,String> kafkaTemplate ;
	
//	@Autowired
//	RedisArticleService redisArticleService;
	@Test
	public void sendKafka() throws Exception {
		
		File file = new File("D:/1708E文件");
		File[] listFiles = file.listFiles();
		int i = 0;
		for (File file2 : listFiles) {
			if(i<=50) {
				i++;
				String name = file2.getName();
				String title = name.replace(".txt", "");
	//			System.out.println(file2);//D:\1708E文件\（月运）App 2020年1月星座运程.txt
				String content= FileUtilIO.readFile(file2, "utf8");
//				FileUtilIO.readFile(file2, "utf-8")
				
				Article article = new Article();
				
				article.setTitle(title);//添加标题
				String substring =null;
				if(content.length()>=140) {
					
					substring = content.substring(0,140);
				}
				article.setContent(substring);//添加内容
				article.setAbstrac("123");//添加摘要
				article.setHits((int)(Math.random()*30*Math.random()));//点击量
				article.setHot(0);//是否热门
				article.setPicture("D:pic");
				article.setUserId(70);
				int array[] = {1,2,3,4,5,6,7,8};
				int index =	(int) (Math.random()*array.length);
				int rand = array[index];
				article.setChannelId(rand);
				
				int array2[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
				int index2 =	(int) (Math.random()*array2.length);
				int rand2 = array[index];
				article.setCategoryId(rand2);
				
				article.setArticleType(0);
//				article.setCreated(new Date(2019, 02, 25));
				System.out.println(article);
//				redisArticleService.addImpl(article);
				//将对象转换成string类型
				String jsonString = JSON.toJSONString(article);
				//发送给kafka  articles
				kafkaTemplate.send("articles", "one"+jsonString);
			}
		}
	}
	
	@Test
	public void rou() {
		int array[] = {1,2,3,4,5,6,7,8};
		for (int i = 0; i < 50; i++) {
//			0.0<=Math.random()<1.0
			int index =	(int) (Math.random()*array.length);
			int rand = array[index];
			System.out.println(array.length);
		}
	}
	@Test
	public void len() {
		for (int i = 0; i < 50; i++) {
			
			System.out.println((int)(Math.random()*30*Math.random()));
		}
	}
}
