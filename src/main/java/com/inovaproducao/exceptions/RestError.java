package com.inovaproducao.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestError extends Exception{
	
	private Object data;
	private Integer status;
	private Long timestamp;
	

	public static RestError notFound(Object data) {
		return new RestError(data, HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
	}

	public static RestError badRequest(Object data) {
		return new RestError(data, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
	}
	
	public RestError(Object data, Integer status, Long timestamp) {
		this.data = data;
		this.status = status;
		this.timestamp = timestamp;
	}

}
