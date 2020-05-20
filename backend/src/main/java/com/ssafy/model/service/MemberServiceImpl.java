package com.ssafy.model.service;

import com.ssafy.model.dto.member.*;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
                .orElseThrow(() -> new AuthException(AuthException.ID_ERROR));
        if (!member.getMemPw().equals(auth.getMemPw())) {
            throw new AuthException(AuthException.PASSWORD_ERROR);
        }
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto regist(MemberSaveRequestDto requestDto) {
        Member memberEntity = mRepo.save(requestDto.toEntity());
        MemberResponseDto member = new MemberResponseDto(memberEntity);
        return member;
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
                .orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));

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
                .orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));

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

    @Transactional
    @Override
    public void deleteMember(int memPk) {
        Member member = mRepo.findById(memPk)
                .orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));
        deleteMember(member);
    }

    @Transactional
    @Override
    public void doLike(int memPk, int objectPk, int objectType, boolean flag) {

        Member member = mRepo.findById(memPk)
                .orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));
        switch (objectType) {
            case 1: // 소설
                Novel novel = nRepo.findById(objectPk)
                        .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));


                if (flag) { // 좋아요
                    if (!member.getLikeNovels().contains(novel)) {
                        // 이미 있으면 넘어가고
                        member.likeNovel(novel);
                        novel.likedMember(member);
                    }
                } else { // 좋아요 취소
                    if (member.getLikeNovels().contains(novel)) {
                        // 이미 있으면 넘어가고
                        member.unLikeNovel(novel);
                        novel.unLikedMember(member);
                    }
                }
                mRepo.save(member);
                nRepo.save(novel);
                break;
            case 2: // 에피소드
                Episode episode = eRepo.findById(objectPk)
                        .orElseThrow(() -> new EpisodeException(EpisodeException.NOT_EXIST));

                if (flag) { // 좋아요
                    if (!member.getLikeEpisodes().contains(episode)) {
                        // 이미 있으면 넘어가고
                        member.likeEpisode(episode);
                        episode.likedMember(member);
                    }
                } else { // 좋아요 취소
                    if (member.getLikeEpisodes().contains(episode)) {
                        // 이미 있으면 넘어가고
                        member.unLikeEpisode(episode);
                        episode.unLikedMember(member);
                    }
                }
                mRepo.save(member);
                eRepo.save(episode);
                break;
            case 3: // 댓글
                Comment comment = cRepo.findById(objectPk)
                        .orElseThrow(() -> new CommentException(CommentException.NOT_EXIST));

                if (flag) { // 좋아요
                    if (!member.getLikeComments().contains(comment)) {
                        // 이미 있으면 넘어가고
                        member.likeComment(comment);
                        comment.likedMember(member);
                    }
                } else { // 좋아요 취소
                    if (member.getLikeComments().contains(comment)) {
                        // 이미 있으면 넘어가고
                        member.unLikeComment(comment);
                        comment.unLikedMember(member);
                    }
                }
                mRepo.save(member);
                cRepo.save(comment);
                break;
            default:
                throw new IllegalArgumentException("요청하신 타입 " + objectPk + "은 없는 타입입니다.");
        }
    }

    @Transactional
    public void deleteAllMembers(){
        List<Member> memberList = mRepo.findAll();
        memberList.forEach(member -> deleteMember(member));
    }

    @Transactional
    public void deleteMember(Member member){
        member.beforeDelete();
        mRepo.save(member);
        mRepo.delete(member);
    }
}
