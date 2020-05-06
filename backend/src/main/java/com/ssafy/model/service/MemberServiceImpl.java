package com.ssafy.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.AuthException;
import com.ssafy.model.dto.Member;
import com.ssafy.model.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberRepository mRepo;
	
	@Override
	public Member login(Auth auth) {
		Member member = mRepo.findByMemId(auth.getMem_id());
		if(member == null) {
			throw new AuthException("존재하지 않는 아이디입니다.");	
		}
		else {
			if(!member.getMemPw().equals(auth.getMem_pw())) {
				throw new AuthException("올바르지 않은 비밀번호입니다.");
			}
			
			return member;
		}
	}
	
	@Override
	public boolean regist(Member member) {
		if(mRepo.findByMemId(member.getMemId()) != null) return false;

		mRepo.save(member);
		return true;
	}
	
	@Override
	public List<Member> getMembers(){
		return mRepo.findAll();
	}
}
