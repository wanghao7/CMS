package com.wanghao.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.wanghao.cms.common.CmsContant;
import com.wanghao.cms.entity.User;

public class CmsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User u = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		
		if(u==null) {
			request.getSession().setAttribute("error","请登录");
			System.out.println("11111111111");
			response.sendRedirect("/user/login");
			return false;
		}
		
		return true;
	}

}
