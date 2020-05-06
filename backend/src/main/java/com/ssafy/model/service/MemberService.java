package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.Member;

public interface MemberService {
	Member login(Auth auth);
	void regist(Member member);
	List<Member> getMembers();
	boolean checkId(String id);
	boolean checkPw(String pw);
	boolean checkNick(String nick);
	boolean checkEmail(String email);
	boolean checkPhone(String phone);
}
