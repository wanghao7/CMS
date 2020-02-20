package com.wanghao.cms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.wanghao.cms.common.Gender;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1853518861168554742L;
	private int  id             ;// int(11)  
	@NotBlank(message="用户名不能为空")
	
	@Size(max=16,min=4,message="用户名长度4-16位")
	private String  username    ;// varchar(20)    
	
	@NotBlank(message="密码不能为空")
	@Size(max=16,min=4,message="密码长度4-16位")
	private String  password    ;// varchar(50)                               
	private String  nickname    ;// varchar(20) 
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date    birthday    ;// date                                      
	private Gender  gender      ;// int(11)                                   
	private int     locked      ;// int(11)       1:正常,0:禁用               
	private String  createTime ;// datetime                                  
	private String  updateTime ;// datetime                                  
	private String  url         ;// varchar(200)                              
	private int     score       ;// int(11)                                   
	private int  role        ;// varchar(1)    0:普通用户,1:管理员      
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", birthday=" + birthday + ", gender=" + gender + ", locked=" + locked + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", url=" + url + ", score=" + score + ", role=" + role + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
