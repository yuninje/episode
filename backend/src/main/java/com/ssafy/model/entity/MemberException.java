package com.ssafy.model.entity;

public class MemberException extends RuntimeException {
	public MemberException() {}
	public MemberException(String msg) {
		super(msg);
	}
}