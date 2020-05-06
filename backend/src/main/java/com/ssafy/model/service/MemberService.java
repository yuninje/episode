package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.Member;

public interface MemberService {
	Member login(Auth auth);
	Boolean regist(Member member);
	List<Member> getMembers();
}
