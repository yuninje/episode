package com.ssafy.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dao.MemberDao;
import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.AuthException;
import com.ssafy.model.dto.Member;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDao mDao;
	
	@Override
	public Member login(Auth auth) {
		Member member = mDao.getMember(auth.getMem_id());
		if(member == null) {
			throw new AuthException("아이디가 존재하지 않음");						
		}else {
			if(member.getMem_pw().equals(auth.getMem_pw())) {
				return member;
			}
			throw new AuthException("비밀번호 틀림");
		}
	}
	@Override
	public Boolean regist(Member member) {
		Boolean result = mDao.registMember(member);
		return result;
	}
	
	@Override
	public List<Member> getMembers(){
		List<Member> members = mDao.getMembers();
		return members;
	}
	
	
}
