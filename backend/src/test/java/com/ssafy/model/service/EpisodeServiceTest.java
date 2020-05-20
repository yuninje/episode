package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class EpisodeServiceTest {

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Member member;
    private Novel novel;
    private Episode episode;
    private Comment comment;
    private int memPk;
    private int novelPk;
    private int episodePk;
    private int commentPk;

    private final int PAGE = 0;
    private final int PAGE_SIZE = 5;
    private final Pageable page = PageRequest.of(PAGE, PAGE_SIZE);

    private final int MEMBER = 0;
    private final int NOVEL = 1;
    private final int EPISODE = 2;
    private final int COMMENT = 3;

    private final String STR = "STR";
    private final String UPDATE_STR = "UPDATE_STR";
    boolean catchFlag;

    @Before
    public void setUp(){
        memberRepository.deleteAll();
        novelRepository.deleteAll();
        episodeRepository.deleteAll();
        commentRepository.deleteAll();
        System.out.println("setUp()");
        setMember();
        setNovel();
        setEpisode();
        setComment();
        catchFlag = false;
    }





    private void setMember(){
        member = Member.builder()
                .memId(STR)
                .memPw(STR)
                .memNick(STR)
                .memEmail(STR)
                .memBirth(STR)
                .memPhone(STR)
                .memGender(true)
                .build();
        member = memberRepository.save(member);
        memPk = member.getMemPk();
        System.out.println("setMember() - member : " + new MemberResponseDto(member));
    }

    private void setNovel(){
        novel = Novel.builder()
                .member(member)
                .novelName(STR)
                .novelImage(STR)
                .novelIntro(STR)
                .novelLimit(true)
                .novelOnly(true)
                .novelOpen(true)
                .novelStatus(0)
                .build();
        novel = novelRepository.save(novel);
        novelPk = novel.getNovelPk();
        System.out.println("setNovel() - novel : " + new NovelResponseDto(novel));
    }

    private void setEpisode(){
        episode = Episode.builder()
                .novel(novel)
                .episodeTitle(STR)
                .episodeContent(STR)
                .episodeCreatedAt(LocalDateTime.now())
                .episodeView(0)
                .episodeWriter(STR)
                .build();

        episode = episodeRepository.save(episode);
        episodePk = episode.getEpisodePk();
        System.out.println("setEpisode() - episode : " + new EpisodeResponseDto(episode));
    }

    private void setComment(){
        comment = Comment.builder()
                .member(member)
                .episode(episode)
                .commentContent(STR)
                .commentCreatedAt(LocalDateTime.now())
                .build();

        comment = commentRepository.save(comment);
        commentPk = comment.getCommentPk();
        System.out.println("setComment() - comment : " + new CommentResponseDto(comment));
    }

    Member memberFindById(int memPk){
        return memberRepository.findById(memPk).orElseThrow(
                () -> new MemberException(MemberException.NOT_EXIST));
    }
    Novel novelFindById(int novelPk){
        return novelRepository.findById(novelPk).orElseThrow(
                () -> new NovelException(NovelException.NOT_EXIST));
    }
    Episode episodeFindById(int episodePk){
        return episodeRepository.findById(episodePk).orElseThrow(
                () -> new EpisodeException(EpisodeException.NOT_EXIST));
    }
    Comment commentFindById(int commentPk){
        return commentRepository.findById(commentPk).orElseThrow(
                () -> new CommentException(CommentException.NOT_EXIST));
    }
}