package com.maintenance.web.context;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 接口返回对象
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Result<Object> result = new Result<>();

	/**
	 * 失败消息
	 */
	private String message;

	/**
	 * 返回代码  
	 */
	private Integer code;

	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	/**
	 * 结果对象
	 */
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private Result() {}

	public static Result<Object> success() {
		Result<Object> result = new Result<>();
		result.setCode(HttpStatus.OK.value());
		return result;
	}

	public static Result<Object> success(Object data) {
		result.setCode(HttpStatus.OK.value());
		result.setData(data);
		return result;
	}

    public static Result<Object> success(Object data, String message) {
        result.setCode(HttpStatus.OK.value());
        result.setData(data);
        result.setMessage(message);
        return result;
    }

	public static Result<Object> failure(String message) {
		result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		result.setMessage(message);
		return result;
	}

	public static Result<Object> failure(Integer code, String message) {
		result.setCode(code);
		result.setMessage(message);
		return result;
	}
}
