package com.ssafy.model.dto.member;

public class AuthException extends RuntimeException {
	public AuthException() {}
	public AuthException(String msg) {
		super(msg);
	}
}
