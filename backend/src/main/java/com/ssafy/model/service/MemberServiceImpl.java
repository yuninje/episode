package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.AuthException;
import com.ssafy.model.dto.MemberDTO;
import com.ssafy.model.entity.Comment;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Member;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberRepository mRepo;
	@Autowired
	CommentRepository cRepo;
	@Autowired
	EpisodeRepository eRepo;
	@Autowired
	NovelRepository nRepo;
	
	
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
	
	@Override
	public void doLike(int memPk, int objectPk, int objectType, boolean flag) {

		Member member = mRepo.findById(memPk).orElseThrow(
			() -> new IllegalArgumentException("요청하신 member의 "+memPk+"가 없습니다.")
		);
		switch(objectType) {
		case 2: // 소설
			
			break;
		case 3: // 에피소드
			Episode episode = eRepo.findById(objectPk).orElseThrow(
					() -> new IllegalArgumentException("요청하신 episode의 "+objectPk+"가 없습니다.")
				);
				
				if(flag) { // 좋아요
					if(!member.getLikeEpisodes().contains(episode)) {
						// 이미 있으면 넘어가고 
						member.getLikeEpisodes().add(episode);
						episode.getLikedMembers().add(member);
					}
				}else { // 좋아요 취소
					if(member.getLikeEpisodes().contains(episode)) {
						// 이미 있으면 넘어가고 
						member.getLikeEpisodes().remove(episode);
						episode.getLikedMembers().remove(member);
					}
				}
				mRepo.save(member);
				eRepo.save(episode);
			break;
		case 6: // 댓글
			Comment comment = cRepo.findById(objectPk).orElseThrow(
				() -> new IllegalArgumentException("요청하신 comment의 "+objectPk+"가 없습니다.")
			);
			
			if(flag) { // 좋아요
				if(!member.getLikeComments().contains(comment)) {
					// 이미 있으면 넘어가고 
					member.getLikeComments().add(comment);
					comment.getLikedMembers().add(member);
				}
			}else { // 좋아요 취소
				if(member.getLikeComments().contains(comment)) {
					// 이미 있으면 넘어가고 
					member.getLikeComments().remove(comment);
					comment.getLikedMembers().remove(member);
				}
			}
			mRepo.save(member);
			cRepo.save(comment);
			break;
		}
	}
}
