package com.ssafy.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.MemberDTO;
import com.ssafy.model.dto.member.Auth;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.member.MemberSaveRequestDto;
import com.ssafy.model.dto.member.MemberUpdateRequestDto;

public interface MemberService {
	MemberResponseDto login(Auth auth);
	void regist(MemberSaveRequestDto requestDto);
	Page<MemberResponseDto> getMembers(Pageable pageable);
	MemberResponseDto getMember(int memPk);
	boolean checkId(String memId);
	boolean checkNick(String memNick);
	boolean checkPw(String memPw);
	boolean checkEmail(String memEmail);
	boolean checkPhone(String memPhone);
	MemberResponseDto updateMember(int memPk, MemberUpdateRequestDto member);
	
	@Transactional
	void deleteMember(int memPk);

	void doLike(int memPk, int objectPk, int objectType, boolean flag);
}
