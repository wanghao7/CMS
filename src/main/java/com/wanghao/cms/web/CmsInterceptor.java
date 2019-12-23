package com.wanghao.cms.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wanghao.cms.service.UserService;
import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.entity.User;

public class CmsInterceptor implements HandlerInterceptor {

	
	@Autowired
	UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User u = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		
		if(u != null)
		{
			return true;
		}
		
		// 从cookie 当中获取用户信息
		User user = new User();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if("username".equals(cookies[i].getName())){
				user.setUsername(cookies[i].getValue());
			}
			if("userpwd".equals(cookies[i].getName())){
				user.setPassword(cookies[i].getValue());
			}
		}
		
		// 如果cookie中存放的用户信息不完整
		if(null==user.getUsername() || null== user.getPassword()) {
			response.sendRedirect("/user/login");
			return false;
		}
		
		// cookie验证登录操作
		u = userService.login(user);
		if(u!=null) {
			request.getSession().setAttribute(CmsContant.USER_KEY, u);
			return true;
		}
		response.sendRedirect("/user/login");
		return false;
	}

}
