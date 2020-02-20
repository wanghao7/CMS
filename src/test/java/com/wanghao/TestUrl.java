package com.wanghao;

import org.junit.Test;

import com.wanghao.cms.utils.StringUtils;

public class TestUrl {

	@Test
	public void ceshi() {
		String url="123";
		//对地址进行测试
		boolean httpUrl = StringUtils.isHttpUrl(url);
		System.out.println(httpUrl);
	}
}
