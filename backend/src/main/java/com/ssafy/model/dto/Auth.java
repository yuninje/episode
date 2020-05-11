package com.ssafy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auth {	// 로그인 관련 클래스
	String memId;		// 아이디
	String memPw;		// 비밀번호
}
