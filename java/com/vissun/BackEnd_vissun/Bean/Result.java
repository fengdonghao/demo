package com.vissun.BackEnd_vissun.Bean;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in16:55 2018/3/28
 */
public class Result<T> {
	private  Integer code;

	private String msg;

	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


}
