package com.artisan.thisishardcore;

public class TIHException extends RuntimeException {
	
	public TIHException(String message, Exception e) {
		super(message, e);
	}
}
