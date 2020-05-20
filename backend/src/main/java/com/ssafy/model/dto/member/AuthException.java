package com.ssafy.model.dto.member;

public class AuthException extends RuntimeException {
	public static String PASSWORD_ERROR ="올바르지 않은 비밀번호입니다.";
	public static String ID_ERROR = "존재하지 않는 아이디입니다.";
	public AuthException() {}
	public AuthException(String msg) {super(msg);}
}