package com.ssafy.model.service;

import java.util.List;

import javax.persistence.EntityManager;

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
	public void regist(Member member) {
		mRepo.save(member);
	}
	
	@Override
	public List<Member> getMembers(){
		return mRepo.findAll();
	}
	
	@Override
	public Member getMember(String id) {
		return mRepo.findByMemId(id);
	}

	@Override
	public boolean checkId(String id) {
		if(mRepo.findByMemId(id) == null) return true;
		
		return false;
	}

	@Override
	public boolean checkNick(String nick) {
		if(mRepo.findByMemNick(nick) == null) return true;
		
		return false;
	}
	
	@Override
	public boolean checkPw(String pw) {
		String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{10,20}$";
		
		return pw.matches(regex);
	}

	@Override
	public boolean checkEmail(String email) {
		if(mRepo.findByMemEmail(email) == null) return true;
		
		return false;
	}

	@Override
	public boolean checkPhone(String phone) {
		if(mRepo.findByMemPhone(phone) == null) return true;
		
		return false;
	}
	
	@Override
	public Member updateMember(Member member) {
		Member updateM = mRepo.findByMemId(member.getMemId());
		
		updateM.setMemEmail(member.getMemEmail());
		updateM.setMemNick(member.getMemNick());
		updateM.setMemPhone(member.getMemPhone());
		updateM.setMemBirth(member.getMemBirth());
		updateM.setMemGender(member.isMemGender());
		
		return mRepo.save(updateM);
	}
	
	@Override
	public void deleteMember(String id) {
		mRepo.deleteByMemId(id);
	}
}
