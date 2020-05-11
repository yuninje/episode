package com.ssafy.model.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.MemberDTO;
import com.ssafy.model.entity.Member;

public interface MemberService {
	MemberDTO login(Auth auth);
	void regist(MemberDTO member);
	List<MemberDTO> getMembers();
	MemberDTO getMember(String id);
	boolean checkId(String id);
	boolean checkNick(String nick);
	boolean checkPw(String pw);
	boolean checkEmail(String email);
	boolean checkPhone(String phone);
	MemberDTO updateMember(MemberDTO member);
	@Transactional
	void deleteMember(String id);
}
