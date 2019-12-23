package com.wanghao.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wanghao.cms.entity.Category;
import com.wanghao.cms.entity.Channel;
import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.User;
import com.wanghao.cms.service.ArticleService;
import com.wanghao.cms.service.UserService;
import com.wanghao.cms.utils.FileUtils;
import com.wanghao.cms.utils.HtmlUtils;
import com.wanghao.cms.utils.StringUtils;

@Controller
@RequestMapping("user")
public class UserController {

	@Value("${upload.path}")
	String picRootPath;
	
	@Value("${pic.path}")
	String picUrl;
	
	static String path;
	@Autowired
	private UserService service;
	
	@Autowired
	ArticleService   articleService;
	
	
	@RequestMapping("home")
	public String home() {
		return "user/home";
	}
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String show() {
		return "user/login";
	}
	//跳转注册页面
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register(Model m) {
		User user = new User();
		m.addAttribute("user",user );
		return "user/register";
	}
	//接收注册页面
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String vali(Model m,@Valid @ModelAttribute("user") User user,BindingResult result ) {
		//有错误返回注册界面
		if(result.hasErrors()) {
			return "user/register";
		}
		System.out.println("111111111111111111111111111111111111");
		//用户名进行唯一性验证
		User user1 = service.getByUserName(user.getUsername());
		
		if(user1!=null) {
			result.rejectValue("username", "", "用户名已存在");
			return "user/register";
		}
		//加一个手动的校验
		if(StringUtils.isNumber(user.getPassword())) {
			result.rejectValue("password", "", "密码不能全是数字");
			return "user/register";
		}
		//注册
		int i = service.register(user);
		
		//注册失败
		if(i<1) {
			m.addAttribute("error", "注册失败,请稍后尝试!");
			return "user/register";
		}
		//跳转登录界面
		return "redirect:login";
	}
	/**
	 * 登出
	 */
	@RequestMapping("logout")
	public String home(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute(CmsContant.USER_KEY);
		
		
		Cookie cookieUserName = new Cookie("username", "");
		cookieUserName.setPath("/");
		cookieUserName.setMaxAge(0);// 覆盖原来的cookieUserName
		response.addCookie(cookieUserName);
		Cookie cookieUserPwd = new Cookie("userpwd", "");
		cookieUserPwd.setPath("/");
		cookieUserPwd.setMaxAge(0);// 覆盖原来的cookieUserPwd
		response.addCookie(cookieUserPwd);
		
		return "redirect:/";
	}
	
	/**
	 * 前台(注册时验证)
	 * @param username
	 * @return
	 */
	@RequestMapping("checkname")
	@ResponseBody
	public boolean checkUserName(String username) {
		User existUser = service.getByUserName(username);
		return existUser==null;
	}
//	@RequestMapping(value="register",method=RequestMethod.POST)
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(User user,HttpServletRequest request,HttpServletResponse response) {
		String pwd =  new String(user.getPassword());
		User u = service.login(user);
		//如果没有这个对象 返回登录界面
		if(u==null) {
			request.setAttribute("error", "用户名或密码错误!");
			return "/user/login";
		}
		//登录成功,用户信息存进session当中
		request.getSession().setAttribute(CmsContant.USER_KEY, u);
		
		//保存用户名的账号和密码
		Cookie cookieUserName = new Cookie("username", user.getUsername());
		//指定客户机应该返回 cookie 的路径
		cookieUserName.setPath("/");
		//返回以秒为单位指定的 cookie 最大生存时间。
		cookieUserName.setMaxAge(10*24*3600);// 10天
		response.addCookie(cookieUserName);
		Cookie cookieUserPwd = new Cookie("userpwd", pwd);
		cookieUserPwd.setPath("/");
		cookieUserPwd.setMaxAge(10*24*3600);// 10天
		response.addCookie(cookieUserPwd);
		
		
		
		
//		System.out.println("admin_____1____");
		//判断 并进入管理界面/用户界面
//		System.out.println(u.getRole()+"______");
		if(u.getRole()==CmsContant.USER_ROLE_ADMIN) {
			System.out.println("________________________-管理员");
//			System.out.println("admin____2_____");
			return "redirect:/admin/index";
		}
//		System.out.println("admin___3______");
		
		
		return "/user/home";
	}
	
	/**
	 * 点击左侧导航条 动态加载页面（我的文章）
	 */
	
	@RequestMapping("articles")
	public String articles(HttpServletRequest request,@RequestParam(defaultValue="1")int pageNum ) {
		
		User user = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		
		PageInfo<Article> articlePage = articleService.listByUser(user.getId(),pageNum);
//		List<Article> list = articlePage.getList();
//		for (Article article : list) {
//			System.out.println(article);
//		}
		request.setAttribute("pg", articlePage);
		//返回article文件夹下的list界面
		return "user/article/list";
	}
	/**
	 * 修改时返回的布尔类型  前台需要ajax弹框
	 * @param id
	 * @return
	 */
	@RequestMapping("deletearticle")
	@ResponseBody
	public boolean deletearticle(Integer id) {
		int result  = articleService.delete(id);
		return result > 0;
	}
	

	/**
	 * 跳转到发布文章的页面
	 * @return
	 */
	@RequestMapping("postArticle")
	public String postArticle(HttpServletRequest request) {	
		List<Channel> channels= articleService.getChannels();
		//所有频道/栏目
		request.setAttribute("channels", channels);
		return "user/article/post";
	}
	
	/**
	 * 前台ajax改变事件
	 * @param request
	 * @param cid
	 * @return
	 */
	@RequestMapping("getCategoris")
	@ResponseBody
	public List<Category>  getCategoris(int cid) {	
		List<Category> categoris = articleService.getCategorisByCid(cid);
		return categoris;
	}
	
	/**
	 * 添加文章
	 */
	@RequestMapping(value="postArticle",method=RequestMethod.POST)
	@ResponseBody
	public boolean postArticle(HttpServletRequest request,Article article,MultipartFile file) {
		String picUrl;
		try {
			//调用rpocessFile方法  对文件地址进行拼接
			picUrl=processFile(file);
			article.setPicture(picUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//当前用户是文章的作者
		User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		article.setUserId(loginUser.getId());
		
		
		return articleService.add(article)>0;
	}
	
	/**
	 * 文件的封装
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String processFile(MultipartFile file) throws IllegalStateException, IOException {
		//判断目标目录时间是否存在
		// picRiitPath + ""
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String subPath = sdf.format(new Date());
		//图片存放的路径
		File path = new File(picRootPath+"/" + subPath);
		//路径不存在则创建
		if(!path.exists()) {
			path.mkdirs();
		}
		//计算新的文件名称
		String suffixName= FileUtils.getSuffixName(file.getOriginalFilename());
		//随机生成文件名.
		String fileName = UUID.randomUUID().toString() + suffixName;
		
		file.transferTo(new File(picRootPath+"/" + subPath + "/" + fileName));
		return subPath + "/" + fileName;
	}
	
	
	/**
	 * 文章的修改
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateArtice",method=RequestMethod.GET)
	private String updateArtice(int id,Model m,HttpServletRequest request) {
		List<Channel> channels= articleService.getChannels();
		//所有频道/栏目
		m.addAttribute("channels", channels);
		
		//获取文章
		Article article =  articleService.getById(id);
//		System.out.println("__________________"+article);
		User user = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		//数据库文章的id跟session里的yonghuid相比较
		if(user.getId()!=article.getUserId()) {
			//  准备做异常处理
		}
		path=article.getPicture();
		m.addAttribute("picture", picUrl+article.getPicture());
		m.addAttribute("article", article);
		m.addAttribute("content1", HtmlUtils.htmlspecialchars(article.getContent()));
		
		return "/user/article/update";
	}
	/**
	 * 修改文章  (service判断)
	 * @param file
	 * @param request
	 * @param article
	 * @return
	 */
	@RequestMapping(value="updateArticle",method=RequestMethod.POST)
	@ResponseBody
	public boolean updateArticle(MultipartFile file,HttpServletRequest request,Article article) {
		String picUrl;
		if(file.isEmpty()) {
			article.setPicture(path);
		}else {
			try {
				// 处理上传文件
				picUrl = processFile(file);
				article.setPicture(picUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//当前用户是文章的作者
		User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		//article.setUserId(loginUser.getId());
		int updateResult  = articleService.update(article,loginUser.getId());
		
		
		return updateResult>0;
	}
	
	
	
}
