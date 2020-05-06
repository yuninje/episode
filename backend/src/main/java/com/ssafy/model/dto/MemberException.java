package com.ssafy.model.dto;

public class MemberException extends RuntimeException {
	public MemberException() {}
	public MemberException(String msg) {
		super(msg);
	}
}