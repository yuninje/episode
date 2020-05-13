package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.AuthException;
import com.ssafy.model.dto.MemberDTO;
import com.ssafy.model.entity.Member;
import com.ssafy.model.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberRepository mRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public MemberDTO login(Auth auth) {
		MemberDTO member = modelMapper.map(mRepo.findByMemId(auth.getMemId()), MemberDTO.class);
		if(member == null) {
			throw new AuthException("존재하지 않는 아이디입니다.");	
		}
		else {
			if(!member.getMemPw().equals(auth.getMemPw())) {
				throw new AuthException("올바르지 않은 비밀번호입니다.");
			}
			
			return member;
		}
	}
	
	@Override
	public void regist(MemberDTO member) {
		mRepo.save(modelMapper.map(member, Member.class));
	}
	
	@Override
	public List<MemberDTO> getMembers(){
		List<Member> results = mRepo.findAll();
		List<MemberDTO> members = 
				results.stream().map(member -> modelMapper.map(member, MemberDTO.class))
				.collect(Collectors.toList());
		
		return members;
	}
	
	@Override
	public MemberDTO getMember(int memPk) {
		Member member = mRepo.findById(memPk).orElse(null);
		
		if(member == null) return null;
		
		return modelMapper.map(member, MemberDTO.class);
	}

	@Override
	public boolean checkId(String memId) {
		if(mRepo.findByMemId(memId) == null) return true;
		
		return false;
	}

	@Override
	public boolean checkNick(String memNick) {
		if(mRepo.findByMemNick(memNick) == null) return true;
		
		return false;
	}
	
	@Override
	public boolean checkPw(String memPw) {
		String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{10,20}$";
		
		return memPw.matches(regex);
	}

	@Override
	public boolean checkEmail(String memEmail) {
		if(mRepo.findByMemEmail(memEmail) == null) return true;
		
		return false;
	}

	@Override
	public boolean checkPhone(String memPhone) {
		if(mRepo.findByMemPhone(memPhone) == null) return true;
		
		return false;
	}
	
	@Override
	public MemberDTO updateMember(int memPk, MemberDTO member) {
		Member updateM = mRepo.findById(memPk).orElse(null);
		
		if(updateM == null) return null;
		
		updateM.setMemEmail(member.getMemEmail());
		updateM.setMemNick(member.getMemNick());
		updateM.setMemPhone(member.getMemPhone());
		updateM.setMemBirth(member.getMemBirth());
		updateM.setMemGender(member.isMemGender());
		
		return modelMapper.map(mRepo.save(updateM), MemberDTO.class);
	}
	
	@Override
	public void deleteMember(int memPk) {
		mRepo.deleteById(memPk);
	}
}
