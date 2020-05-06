package com.ssafy.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	int mem_pk;			// PK
	String mem_id;		// 아이디
	String mem_email;	// 이메일
	String mem_pw;		// 비밀번호
	String mem_nick;	// 닉네임
	String mem_phone;	// 전화번호
	String mem_birth;  	// 생년월일
	Boolean mem_gender; 	// 성별
	
	public Member() {}
	public Member(int mem_pk, String mem_id, String mem_email, String mem_pw, String mem_nick, String mem_phone,
			String mem_birth, Boolean mem_gender) {
		super();
		this.mem_pk = mem_pk;
		this.mem_id = mem_id;
		this.mem_email = mem_email;
		this.mem_pw = mem_pw;
		this.mem_nick = mem_nick;
		this.mem_phone = mem_phone;
		this.mem_birth = mem_birth;
		
		this.mem_gender = mem_gender;
	}
}
