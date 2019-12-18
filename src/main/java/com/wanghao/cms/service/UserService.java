package com.wanghao.cms.service;

import javax.validation.Valid;

import com.wanghao.cms.entity.User;

public interface UserService {

	User getByUserName(String username);

	int register(@Valid User user);

	User login(User user);

}
