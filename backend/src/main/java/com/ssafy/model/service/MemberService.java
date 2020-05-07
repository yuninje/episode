package com.ssafy.model.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.Member;

public interface MemberService {
	Member login(Auth auth);
	void regist(Member member);
	List<Member> getMembers();
	Member getMember(String id);
	boolean checkId(String id);
	boolean checkNick(String nick);
	boolean checkPw(String pw);
	boolean checkEmail(String email);
	boolean checkPhone(String phone);
	Member updateMember(Member member);
	@Transactional
	void deleteMember(String id);
}
