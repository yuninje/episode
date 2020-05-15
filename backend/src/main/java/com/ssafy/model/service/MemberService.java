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
	MemberDTO getMember(int memPk);
	boolean checkId(String memId);
	boolean checkNick(String memNick);
	boolean checkPw(String memPw);
	boolean checkEmail(String memEmail);
	boolean checkPhone(String memPhone);
	MemberDTO updateMember(int memPk, MemberDTO member);
	@Transactional
	void deleteMember(int memPk);

	void doLike(int memPk, int objectPk, int objectType, boolean flag);
}
