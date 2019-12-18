package com.wanghao.cms.dao;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.wanghao.cms.entity.User;

public interface UserMapper {
	/**
	 * 根据用户名查找(唯一性)
	 * @param username
	 * @return
	 */
	@Select(" select username from cms_user "
			+ " where username=#{value} limit 1 ")
	User getByUserName(String username);

	/**
	 * 注册用户(添加)
	 * @param user
	 * @return
	 */
	@Insert("INSERT INTO cms_user(username,password,locked,create_time,score,role)"
			+ " VALUES(#{username},#{password},0,now(),0,0)")
	int register(@Valid User user);

	/**
	 * 用户登录(查找)
	 * @param user
	 * @return
	 */
	@Select("SELECT id,username,password,nickname,birthday,"
			+ "gender,locked,create_time createTime,update_time updateTime,url,"
			+ "role FROM cms_user WHERE username=#{username}  AND password = #{password} "
			+ " LIMIT 1")
	User login(User user);

}
