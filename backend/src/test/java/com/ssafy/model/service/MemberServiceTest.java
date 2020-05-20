package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.member.*;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.repository.NovelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

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

    private final int MEMBER = 0;
    private final int NOVEL = 1;
    private final int EPISODE = 2;
    private final int COMMENT = 3;

    private final int PAGE = 0;
    private final int PAGE_SIZE = 5;
    private final Pageable page = PageRequest.of(PAGE, PAGE_SIZE);

    private final String STR = "STR";
    private final String UPDATE_STR = "UPDATE_STR";
    boolean catchFlag;
    @Before
    public void setUp(){
        System.out.println("setUp()");
        memberRepository.deleteAll();
        novelRepository.deleteAll();
        episodeRepository.deleteAll();
        commentRepository.deleteAll();
        setMember();
        setNovel();
        setEpisode();
        setComment();
        catchFlag = false;
    }

    @Test
    public void 로그인_성공() {
        Auth auth = new Auth(STR, STR);
        MemberResponseDto responseDto = memberService.login(auth);

        assertThat(responseDto.getMemId()).isEqualTo(STR);
        assertThat(responseDto.getMemBirth()).isEqualTo(STR);
        assertThat(responseDto.getMemEmail()).isEqualTo(STR);
        assertThat(responseDto.getMemGender()).isEqualTo(true);
        assertThat(responseDto.getMemNick()).isEqualTo(STR);
        assertThat(responseDto.getMemPhone()).isEqualTo(STR);
    }

    @Test
    public void 로그인_실패_비밀번호_틀림() {
        Auth auth = new Auth(STR, UPDATE_STR);
        try{
            MemberResponseDto memberResponseDto = memberService.login(auth);
        }catch (AuthException e){
            catchFlag = true;
            assertThat(e.getMessage()).isEqualTo(AuthException.PASSWORD_ERROR);
        }
        assertThat(catchFlag).isEqualTo(true);
    }
    @Test
    public void 로그인_실패_아이디_없음() {
        Auth auth = new Auth(UPDATE_STR, UPDATE_STR);
        try{
            MemberResponseDto memberResponseDto = memberService.login(auth);
        }catch (AuthException e){
            catchFlag = true;
            assertThat(e.getMessage()).isEqualTo(AuthException.ID_ERROR);
        }
        assertThat(catchFlag).isEqualTo(true);
    }

    @Test
    public void 회원가입_성공() {
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .memId(UPDATE_STR)
                .memPw(UPDATE_STR)
                .memNick(UPDATE_STR)
                .memEmail(UPDATE_STR)
                .memBirth(UPDATE_STR)
                .memPhone(UPDATE_STR)
                .memGender(true)
                .build();

        MemberResponseDto responseDto = memberService.regist(requestDto);
        Member member = memberFindById(responseDto.getMemPk());

        assertThat(member.getMemId()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemBirth()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemEmail()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemGender()).isEqualTo(true);
        assertThat(member.getMemNick()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemPhone()).isEqualTo(UPDATE_STR);
    }
    @Test
    public void 회원가입_실패_아이디_중복() {
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .memId(STR)
                .memPw(UPDATE_STR)
                .memNick(UPDATE_STR)
                .memEmail(UPDATE_STR)
                .memBirth(UPDATE_STR)
                .memPhone(UPDATE_STR)
                .memGender(true)
                .build();
        MemberResponseDto responseDto;
        try{
            responseDto = memberService.regist(requestDto); // 두번째 저장
        }catch(Exception e){
            catchFlag = true;
        }

        assertThat(catchFlag).isEqualTo(true);
    }
    @Test
    public void 회원가입_실패_닉네임_중복() {
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .memId(UPDATE_STR)
                .memPw(UPDATE_STR)
                .memNick(STR)
                .memEmail(UPDATE_STR)
                .memBirth(UPDATE_STR)
                .memPhone(UPDATE_STR)
                .memGender(true)
                .build();
        MemberResponseDto responseDto;
        try{
            responseDto = memberService.regist(requestDto); // 두번째 저장
        }catch(Exception e){
            catchFlag = true;
        }

        assertThat(catchFlag).isEqualTo(true);
    }

    @Test
    public void 회원가입_실패_이메일_중복() {
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .memId(UPDATE_STR)
                .memPw(UPDATE_STR)
                .memNick(UPDATE_STR)
                .memEmail(STR)
                .memBirth(UPDATE_STR)
                .memPhone(UPDATE_STR)
                .memGender(true)
                .build();
        MemberResponseDto responseDto;
        try{
            responseDto = memberService.regist(requestDto); // 두번째 저장
        }catch(Exception e){
            catchFlag = true;
        }

        assertThat(catchFlag).isEqualTo(true);
    }

    @Test
    public void 회원가입_실패_핸드폰_중복() {
        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .memId(UPDATE_STR)
                .memPw(UPDATE_STR)
                .memNick(UPDATE_STR)
                .memEmail(UPDATE_STR)
                .memBirth(UPDATE_STR)
                .memPhone(STR)
                .memGender(true)
                .build();
        MemberResponseDto responseDto;
        try{
            responseDto = memberService.regist(requestDto); // 두번째 저장
        }catch(Exception e){
            catchFlag = true;
        }

        assertThat(catchFlag).isEqualTo(true);
    }

    @Test
    public void 회원_PAGE_가져오기() {
        Page<MemberResponseDto> responseDtoPage =
                memberService.getMembers(PageRequest.of(PAGE,PAGE_SIZE));

        assertThat(responseDtoPage.getContent().size()).isLessThanOrEqualTo(5);
        assertThat(responseDtoPage.getContent().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void 회원_하나_가져오기() {
        MemberResponseDto responseDto = memberService.getMember(memPk);

        assertThat(responseDto.getMemId()).isEqualTo(STR);
        assertThat(responseDto.getMemBirth()).isEqualTo(STR);
        assertThat(responseDto.getMemEmail()).isEqualTo(STR);
        assertThat(responseDto.getMemGender()).isEqualTo(true);
        assertThat(responseDto.getMemNick()).isEqualTo(STR);
        assertThat(responseDto.getMemPhone()).isEqualTo(STR);
    }

    @Test
    public void 회원_수정() {
        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .memPw(UPDATE_STR)
                .memNick(UPDATE_STR)
                .memEmail(UPDATE_STR)
                .memBirth(UPDATE_STR)
                .memPhone(UPDATE_STR)
                .memGender(false)
                .build();

        memberService.updateMember(memPk, requestDto);

        Member member = memberFindById(memPk);
        assertThat(member.getMemBirth()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemEmail()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemGender()).isEqualTo(false);
        assertThat(member.getMemNick()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemPhone()).isEqualTo(UPDATE_STR);
    }

    @Transactional
    @Test
    public void 회원_삭제() {
        Member member = memberFindById(memPk);

        // 삭제되어야 하는 데이터들
        List<Novel> novels = member.getNovels();
        List<Comment> comments = member.getComments();
        List<Novel> likeNovels = member.getLikeNovels();
        List<Episode> likeEpsodes = member.getLikeEpisodes();
        List<Comment> likeComments = member.getLikeComments();

        memberService.deleteMember(memPk);
        try {
            memberRepository.findById(memPk).orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));
        }catch (MemberException e){
            assertThat(e.getMessage()).isEqualTo(MemberException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);


        // 회원의 소설들 삭제 확인
        for(Novel novel : novels){
            catchFlag = false;
            try{
                novelFindById(novel.getNovelPk());
            }catch (NovelException e){
                assertThat(e.getMessage()).isEqualTo(NovelException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 회원이 작성한 댓글들 삭제 확인
        for(Comment comment : comments){
            catchFlag = false;
            try{
                commentFindById(comment.getCommentPk());
            }catch (CommentException e){
                assertThat(e.getMessage()).isEqualTo(CommentException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 회원의 소설 좋아요들 삭제 확인
        for(Novel novel : likeNovels){
            catchFlag = false;
            try{
                novelFindById(novel.getNovelPk());
            }catch (NovelException e){
                assertThat(e.getMessage()).isEqualTo(NovelException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }


        // 회원의 에피소드 좋아요들 삭제 확인
        for(Episode episode : likeEpsodes){
            catchFlag = false;
            try{
                episodeFindById(episode.getEpisodePk());
            }catch (EpisodeException e){
                assertThat(e.getMessage()).isEqualTo(EpisodeException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 회원의 댓글 좋아요들 삭제 확인
        for(Comment comment : likeComments){
            catchFlag = false;
            try{
                commentFindById(comment.getCommentPk());
            }catch (CommentException e){
                assertThat(e.getMessage()).isEqualTo(CommentException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

    }
    @Transactional
    @Test
    public void 좋아요_타입_실패() {
        try{
            memberService.doLike(memPk, 1, 100, true);
        }catch (Exception e){
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);
    }
    @Transactional
    @Test
    public void 좋아요_소설() {
        memberService.doLike(memPk, novelPk, NOVEL, true);
        novel = novelFindById(novelPk);
        member = memberFindById(memPk);

        assertThat(member.getLikeNovels()).contains(novel);
        assertThat(novel.getLikedMembers()).contains(member);
    }

    @Transactional
    @Test
    public void 좋아요_에피소드() {
        memberService.doLike(memPk, episodePk, EPISODE, true);
        episode = episodeFindById(episodePk);
        member = memberFindById(memPk);

        assertThat(member.getLikeEpisodes()).contains(episode);
        assertThat(episode.getLikedMembers()).contains(member);
    }
    @Transactional
    @Test
    public void 좋아요_댓글() {
        memberService.doLike(memPk, commentPk, COMMENT, true);
        comment = commentFindById(commentPk);
        member = memberFindById(memPk);

        assertThat(member.getLikeComments()).contains(comment);
        assertThat(comment.getLikedMembers()).contains(member);
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