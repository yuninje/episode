package com.ssafy.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth {	// 로그인 관련 클래스
	String mem_id;		// 아이디
	String mem_pw;		// 비밀번호
	
	public Auth() {}
	public Auth(String mem_id, String mem_pw) {
		super();
		this.mem_id = mem_id;
		this.mem_pw = mem_pw;
	}
}
