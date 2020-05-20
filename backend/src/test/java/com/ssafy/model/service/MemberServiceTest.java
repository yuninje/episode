package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.member.*;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private NovelService novelService;
    @Autowired
    private EpisodeService episodeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GenreRepository genreRepository;

    private Member member;
    private Novel novel;
    private Episode episode;
    private Comment comment;
    private Genre genre;
    private int memPk;
    private int novelPk;
    private int episodePk;
    private int commentPk;
    private int genrePk;

    private final int COUNT = 10;
    private final int MEMBER = 0;
    private final int NOVEL = 1;
    private final int EPISODE = 2;
    private final int COMMENT = 3;

    private final int PAGE = 0;
    private final int PAGE_SIZE = 5;
    private final Pageable page = PageRequest.of(PAGE, PAGE_SIZE);

    private final String STR = "STR";
    private final String STR0 = "STR0";
    private final String UPDATE_STR = "UPDATE_STR";
    boolean catchFlag;

    @Before
    public void setUp(){
        setMember();
        setNovel();
        setEpisode();
        setComment();
        setGenres();
        catchFlag = false;
    }

    @After
    public void cleanUp(){}

    @Test
    public void 로그인_성공() {
        Auth auth = new Auth(STR0, STR0);
        MemberResponseDto responseDto = memberService.login(auth);

        assertThat(responseDto.getMemId()).isEqualTo(STR0);
        assertThat(responseDto.getMemBirth()).isEqualTo(STR0);
        assertThat(responseDto.getMemEmail()).isEqualTo(STR0);
        assertThat(responseDto.getMemGender()).isEqualTo(true);
        assertThat(responseDto.getMemNick()).isEqualTo(STR0);
        assertThat(responseDto.getMemPhone()).isEqualTo(STR0);
    }

    @Test
    public void 로그인_실패_비밀번호_틀림() {
        Auth auth = new Auth(STR0, UPDATE_STR);
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
        member = memberFindById(responseDto.getMemPk());

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
                .memId(STR0)
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
                .memNick(STR0)
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
                .memEmail(STR0)
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
                .memPhone(STR0)
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
                memberService.getMembers(page);

        List<MemberResponseDto> responseDtoList = responseDtoPage.getContent();
        assertThat(responseDtoList.size()).isEqualTo(PAGE_SIZE);
    }

    @Test
    public void 회원_하나_가져오기() {
        MemberResponseDto responseDto = memberService.getMember(memPk);

        assertThat(responseDto.getMemId()).isEqualTo(member.getMemId());
        assertThat(responseDto.getMemBirth()).isEqualTo(member.getMemBirth());
        assertThat(responseDto.getMemEmail()).isEqualTo(member.getMemEmail());
        assertThat(responseDto.getMemGender()).isEqualTo(true);
        assertThat(responseDto.getMemNick()).isEqualTo(member.getMemNick());
        assertThat(responseDto.getMemPhone()).isEqualTo(member.getMemPhone());
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

        assertThat(member.getMemPw()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemBirth()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemEmail()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemGender()).isEqualTo(false);
        assertThat(member.getMemNick()).isEqualTo(UPDATE_STR);
        assertThat(member.getMemPhone()).isEqualTo(UPDATE_STR);
    }

    @Test
    public void 회원_삭제() {
            //    삭제되어야 하는 데이터들
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
        assertThat(member.getLikeNovels().contains(novel)).isEqualTo(false);


        // 회원의 에피소드 좋아요들 삭제 확인
        assertThat(member.getLikeEpisodes().contains(episode)).isEqualTo(false);

        // 회원의 댓글 좋아요들 삭제 확인
        assertThat(member.getLikeComments().contains(comment)).isEqualTo(false);

    }

    @Test
    public void 좋아요_타입_실패() {
        try{
            memberService.doLike(memPk, 1, 100, true);
        }catch (Exception e){
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);
    }
    @Test
    public void 좋아요_소설() {
        memberService.doLike(memPk, novelPk, NOVEL, true);
        novel = novelFindById(novelPk);
        member = memberFindById(memPk);

        assertThat(member.getLikeNovels()).contains(novel);
        assertThat(novel.getLikedMembers()).contains(member);
    }

    @Test
    public void 좋아요_에피소드() {
        memberService.doLike(memPk, episodePk, EPISODE, true);
        episode = episodeFindById(episodePk);
        member = memberFindById(memPk);

        assertThat(member.getLikeEpisodes()).contains(episode);
        assertThat(episode.getLikedMembers()).contains(member);
    }
    @Test
    public void 좋아요_댓글() {
        memberService.doLike(memPk, commentPk, COMMENT, true);
        comment = commentFindById(commentPk);
        member = memberFindById(memPk);

        assertThat(member.getLikeComments()).contains(comment);
        assertThat(comment.getLikedMembers()).contains(member);
    }

    private void setMember(){
        for(int i = 0; i< COUNT; i++){
            String str = STR+i;
            member = Member.builder()
                    .memId(str)
                    .memPw(str)
                    .memNick(str)
                    .memEmail(str)
                    .memBirth(str)
                    .memPhone(str)
                    .memGender(true)
                    .build();
            member = memberRepository.save(member);
            memPk = member.getMemPk();
            System.out.println(new MemberResponseDto(member));
        }
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
        System.out.println(new NovelResponseDto(novel));
    }

    private void setEpisode(){
        for(int i = 0; i < COUNT; i++){
            String str = STR +i;
            episode = Episode.builder()
                    .novel(novel)
                    .episodeTitle(str)
                    .episodeContent(str)
                    .episodeCreatedAt(LocalDateTime.now())
                    .episodeView(0)
                    .episodeWriter(str)
                    .build();

            episode = episodeRepository.save(episode);
            episodePk = episode.getEpisodePk();
            System.out.println(new EpisodeResponseDto(episode));
        }
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
        System.out.println(new CommentResponseDto(comment));
    }
    private void setGenres(){
        for(int i = 1; i<=COUNT; i++){
            genre = Genre.builder()
                    .genreName(STR+i)
                    .build();
            genre = genreRepository.save(genre);
            genrePk = genre.getGenrePk();
            System.out.println(new GenreResponseDto(genre));
        }
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
    Genre genreFindById(int genrePk){
        return genreRepository.findById(genrePk).orElseThrow(
                () -> new GenreException(GenreException.NOT_EXIST));
    }
}