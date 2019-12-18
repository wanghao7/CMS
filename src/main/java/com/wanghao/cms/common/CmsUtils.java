package com.wanghao.cms.common;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author zhuzg
 *
 */
public class CmsUtils {
	
	
	/**
	 *  加盐加密
	 * @param src  明文
	 * @param salt  盐
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encry(String src,String salt) {
		
		byte[] md5 = DigestUtils.md5(salt + src + salt);
		
		 try {
			String enPwd = new  String(md5,"gbk");
			return enPwd;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return salt + src + salt;
			
		}
		
	}
}
