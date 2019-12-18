package com.wanghao.cms.common;

import java.io.Serializable;

public class CmsMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4064295416656676530L;

	int code; //1 成功 2 其他原因
	String error;  //失败的原因
	Object data; //成功返回的对象
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public CmsMessage(int code, String error, Object data) {
		super();
		this.code = code;
		this.error = error;
		this.data = data;
	}
	@Override
	public String toString() {
		return "CmsMessage [code=" + code + ", error=" + error + ", data=" + data + "]";
	}
	public CmsMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
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
		CmsMessage other = (CmsMessage) obj;
		if (code != other.code)
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		return true;
	}
	
}
