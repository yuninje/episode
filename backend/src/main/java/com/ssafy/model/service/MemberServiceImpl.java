package com.ssafy.model.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.member.Auth;
import com.ssafy.model.dto.member.AuthException;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.member.MemberSaveRequestDto;
import com.ssafy.model.dto.member.MemberUpdateRequestDto;
import com.ssafy.model.entity.Comment;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Member;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;

@Service
public class MemberServiceImpl implements MemberService {
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
	public MemberResponseDto login(Auth auth) {
		Member member = mRepo.findByMemId(auth.getMemId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
		if(! member.getMemPw().equals(auth.getMemPw())) {
			throw new AuthException("올바르지 않은 비밀번호입니다.");			
		}
		return new MemberResponseDto(member);
	}

	@Override
	public void regist(MemberSaveRequestDto requestDto) {
		mRepo.save(requestDto.toEntity());
	}

	@Override
	public Page<MemberResponseDto> getMembers(Pageable pageable) {
		Page<Member> results = mRepo.findAll(pageable);
		Page<MemberResponseDto> members = results.map(member -> new MemberResponseDto(member));

		return members;
	}

	@Override
	public MemberResponseDto getMember(int memPk) {
		Member member = mRepo.findById(memPk)
				.orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));

		return new MemberResponseDto(member);
	}

	@Override
	public boolean checkId(String memId) {
		Member member = mRepo.findByMemId(memId).orElse(null);
		if (member == null)
			return true;

		return false;
	}

	@Override
	public boolean checkNick(String memNick) {
		if (mRepo.findByMemNick(memNick) == null)
			return true;

		return false;
	}

	@Override
	public boolean checkPw(String memPw) {
		String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{10,20}$";

		return memPw.matches(regex);
	}

	@Override
	public boolean checkEmail(String memEmail) {
		if (mRepo.findByMemEmail(memEmail) == null)
			return true;

		return false;
	}

	@Override
	public boolean checkPhone(String memPhone) {
		if (mRepo.findByMemPhone(memPhone) == null)
			return true;

		return false;
	}

	@Override
	public MemberResponseDto updateMember(int memPk, MemberUpdateRequestDto requestDto) {
		Member member = mRepo.findById(memPk)
				.orElseThrow(() -> new IllegalArgumentException("member pk :  " + memPk + "가 존재하지 않습니다."));

		member.update(
				requestDto.getMemEmail(), 
				requestDto.getMemPw(), 
				requestDto.getMemNick(), 
				requestDto.getMemPhone(), 
				requestDto.getMemBirth(), 
				requestDto.isMemGender());
		mRepo.save(member);
		return new MemberResponseDto(member);
	}

	@Override
	public void deleteMember(int memPk) {
		mRepo.deleteById(memPk);
	}

	@Override
	public void doLike(int memPk, int objectPk, int objectType, boolean flag) {

		Member member = mRepo.findById(memPk)
				.orElseThrow(() -> new IllegalArgumentException("요청하신 member의 " + memPk + "가 없습니다."));
		switch (objectType) {
		case 2: // 소설

			break;
		case 3: // 에피소드
			Episode episode = eRepo.findById(objectPk)
					.orElseThrow(() -> new IllegalArgumentException("요청하신 episode의 " + objectPk + "가 없습니다."));

			if (flag) { // 좋아요
				if (!member.getLikeEpisodes().contains(episode)) {
					// 이미 있으면 넘어가고
					member.getLikeEpisodes().add(episode);
					episode.getLikedMembers().add(member);
				}
			} else { // 좋아요 취소
				if (member.getLikeEpisodes().contains(episode)) {
					// 이미 있으면 넘어가고
					member.getLikeEpisodes().remove(episode);
					episode.getLikedMembers().remove(member);
				}
			}
			mRepo.save(member);
			eRepo.save(episode);
			break;
		case 6: // 댓글
			Comment comment = cRepo.findById(objectPk)
					.orElseThrow(() -> new IllegalArgumentException("요청하신 comment의 " + objectPk + "가 없습니다."));

			if (flag) { // 좋아요
				if (!member.getLikeComments().contains(comment)) {
					// 이미 있으면 넘어가고
					member.getLikeComments().add(comment);
					comment.getLikedMembers().add(member);
				}
			} else { // 좋아요 취소
				if (member.getLikeComments().contains(comment)) {
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
