package com.wanghao.cms.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wanghao.cms.common.CmsUtils;
import com.wanghao.cms.dao.UserMapper;
import com.wanghao.cms.entity.Article;
import com.wanghao.cms.entity.User;
import com.wanghao.cms.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;

	@Override
	public User getByUserName(String username) {
		// TODO Auto-generated method stub
		return mapper.getByUserName(username);
	}

	@Override
	public int register(@Valid User user) {
		// TODO Auto-generated method stub
		//加盐加密
		String encry = CmsUtils.encry(user.getPassword(), user.getUsername().substring(2, 4));
		
		user.setPassword(encry);
		return mapper.register(user);
	}

	/**
	 * 登录查询密码加盐加密
	 */
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		if(user.getUsername().length()>3) {
			String encry = CmsUtils.encry(user.getPassword(), user.getUsername().substring(2, 4));
			user.setPassword(encry);
		}
		return mapper.login(user);
	}

	
	
}
