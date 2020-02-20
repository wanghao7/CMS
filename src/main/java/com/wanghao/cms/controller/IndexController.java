package com.wanghao.cms.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wanghao.cms.entity.Category;
import com.github.pagehelper.PageInfo;
import com.wanghao.cms.dao.ArticleRep;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.Channel;
import com.wanghao.cms.entity.Slide;
import com.wanghao.cms.service.ArticleService;
import com.wanghao.cms.util.HLUtils;

@Controller
public class IndexController {

	@Autowired
	ArticleService articleService;
	
	//自动注入redisRemplate
	@Autowired
	RedisTemplate redisTemplate;
	
	//es对象
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
//	//es仓库
//	@Autowired
//	ArticleRep articleRep ;
	
	//es搜索方法
	@GetMapping("index")
	public String getEs(String key,Model m,@RequestParam(defaultValue="1") int page,HttpServletRequest request ) {
		
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				//获取所有栏目
				List<Channel> channels = articleService.getChannels();
				m.addAttribute("channels", channels);
				
			}
		};
		t1.start();
		
		Thread t3 = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				
				
				//先获取redis里的数据
				List range = redisTemplate.opsForList().range("new_articles", 0, -1);
				//如果数据库里面没有数据
				if(range==null || range.size()==0) {
					List<Article> lastArticles= articleService.lastLiat();
					System.err.println("从mysql中查询了最新文章...");
					//从数据库里面获取数据并装入redis缓存
					redisTemplate.opsForList().leftPushAll("new_articles",lastArticles.toArray());
					//设置过期时间为5分钟
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);
					//获取最新文章
					m.addAttribute("lastArticles", lastArticles);
				}else {//否则
//					List range2 = redisTemplate.opsForList().range("new_articles", 0, -1);
					System.err.println("从redis中查询了最新文章...");
					m.addAttribute("lastArticles", range);
				}
			}
		};
		t3.start();
//		List<Article> list = articleRep.findByTitle(key);
//		PageInfo<Article> pageInfo = new PageInfo<>(list);
//		m.addAttribute("pageInfo", pageInfo);
		long currentTimeMillis = System.currentTimeMillis();
		PageInfo<Article> pageInfo  = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, page, 5, new String[] {"title"}, "id", key);
		System.out.println(pageInfo);
		long currentTimeMillis2 = System.currentTimeMillis();
		System.out.println("总耗时:"+(currentTimeMillis2-currentTimeMillis));
		m.addAttribute("articlePage", pageInfo);
		m.addAttribute("key", key);
		return "index";
	}
	
	@RequestMapping(value= {"index","/"})
	public String index(Model m,@RequestParam(defaultValue="1") int page) throws InterruptedException {
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				//获取所有栏目
				List<Channel> channels = articleService.getChannels();
				m.addAttribute("channels", channels);
				
			}
		};
		
		Thread t2 = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				//获取热门文章
				List<Article> range = redisTemplate.opsForList().range("hot_articles", 0, -1);
				if(range==null || range.size()==0) {
					System.err.println("从数据库查询热点文章");
					PageInfo<Article> hotList = articleService.hotList(page);
					redisTemplate.opsForList().leftPushAll("hot_articles", hotList.getList().toArray());
					redisTemplate.expire("hot_articles", 5, TimeUnit.MINUTES);
					m.addAttribute("articlePage", hotList);
				}else {
					System.err.println("从redis查询热点文章");

					PageInfo<Article> list = new PageInfo<>(range);
					m.addAttribute("articlePage", list);
				}
//				PageInfo<Article> articlePage = articleService.hotList(page);
//				
//				m.addAttribute("articlePage", articlePage);
			}
		};
		
		Thread t3 = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				//先获取redis里的数据 如果redis没有数据库创建则创建
				List range = redisTemplate.opsForList().range("new_articles", 0, -1);
				//如果数据库里面没有数据
				if(range==null || range.size()==0) {
					List<Article> lastArticles= articleService.lastLiat();
					System.err.println("从mysql中查询了最新文章...");
					//从数据库里面获取数据并装入redis缓存
					redisTemplate.opsForList().leftPushAll("new_articles",lastArticles.toArray());
					//设置过期时间为5分钟
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);
					//获取最新文章
					m.addAttribute("lastArticles", lastArticles);
				}else {//否则
//					List range2 = redisTemplate.opsForList().range("new_articles", 0, -1);
					System.err.println("从redis中查询了最新文章...");
					m.addAttribute("lastArticles", range);
				}
			}
		};
		Thread t4 = new Thread() {
			@Override
			public void run() {
				//获取轮播图的列表
				List<Slide> slides= articleService.getSlides();
				m.addAttribute("slides", slides);
			}
		};
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		return "index";
	}
	/**
	 * 
	  * @param request  请求
	 * @param channleId  栏目的id
	 * @param catId 分类的id
	 * @param page 页码
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping("channel")
	public String channel(HttpServletRequest request,int channelId,
			@RequestParam(defaultValue="0") int catId,
			@RequestParam(defaultValue="1") int page) throws InterruptedException {
		
		Thread  t1 =  new Thread() {
			public void run() {
		// 获取所有的栏目
		List<Channel> channels = articleService.getChannels();
		
		request.setAttribute("channels", channels);
			};
		};
		
		Thread  t2 =  new Thread() {
			public void run() {
		// 当前栏目下  当前分类下的文章
		PageInfo<Article> articlePage= articleService.getArticles(channelId,catId, page);
		request.setAttribute("articlePage", articlePage);
			};
		};
		
		
		Thread t3 = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				//先获取redis里的数据 如果redis没有数据库创建则创建
				List range = redisTemplate.opsForList().range("new_articles", 0, -1);
				//如果数据库里面没有数据
				if(range==null || range.size()==0) {
					List<Article> lastArticles= articleService.lastLiat();
					System.err.println("从mysql中查询了最新文章...");
					//从数据库里面获取数据并装入redis缓存
					redisTemplate.opsForList().leftPushAll("new_articles",lastArticles.toArray());
					//设置过期时间为5分钟
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);
					//获取最新文章
					request.setAttribute("lastArticles", lastArticles);
				}else {//否则
//					List range2 = redisTemplate.opsForList().range("new_articles", 0, -1);
					System.err.println("从redis中查询了最新文章...");
					request.setAttribute("lastArticles", range);
				}
			}
		};
		
		Thread  t4 =  new Thread() {
			public void run() {
		// 轮播图
		List<Slide> slides = articleService.getSlides();
		request.setAttribute("slides", slides);
		
			};
		};
		
		// 获取当前栏目下的所有的分类 catId
		Thread  t5 =  new Thread() {
			public void run() {
		// 
		List<Category> categoris= articleService.getCategoriesByChannelId(channelId);
		request.setAttribute("categoris", categoris);
			};
		};
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		
		// 参数回传
		request.setAttribute("catId", catId);
		request.setAttribute("channelId", channelId);
		
		return "channel";
		
	}
}
