package com.inovaproducao.exceptions;

import java.io.Serializable;

public class FileException extends RuntimeException implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public FileException(String message) {
		super(message);
	}
	
	public FileException(String message, Throwable cause) {
		super(message, cause);
	}


}