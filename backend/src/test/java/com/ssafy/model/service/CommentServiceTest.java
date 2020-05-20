package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.comment.CommentSaveRequestDto;
import com.ssafy.model.dto.comment.CommentUpdateRequestDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
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
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

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
    public void 댓글_페이지로_가져오기(){
        Page<CommentResponseDto> responseDtoPage = commentService.getComments(page);
        List<CommentResponseDto> responseDtoList = responseDtoPage.getContent();

        assertThat(responseDtoList.size()).isEqualTo(PAGE_SIZE);
    }

    @Test
    public void 댓글_하나_가져오기(){
        CommentResponseDto responseDto = commentService.getComment(commentPk);

        assertThat(responseDto.getCommentPk()).isEqualTo(comment.getCommentPk());
        assertThat(responseDto.getCommentCreatedAt()).isEqualTo(comment.getCommentCreatedAt());
        assertThat(responseDto.getCommentContent()).isEqualTo(comment.getCommentContent());
    }

    @Test
    public void 댓글_추가(){

        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .memPk(memPk)
                .episodePk(episodePk)
                .commentContent(STR)
                .build();
        CommentResponseDto responseDto = commentService.registComment(requestDto);

        comment = commentFindById(responseDto.getCommentPk());
        assertThat(responseDto.getCommentContent()).isEqualTo(comment.getCommentContent());
    }

    @Test
    public void 댓글_삭제(){
        commentService.deleteComment(commentPk);
        try {
            commentRepository.findById(commentPk).orElseThrow(() -> new CommentException(CommentException.NOT_EXIST));
        }catch (CommentException e){
            assertThat(e.getMessage()).isEqualTo(CommentException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);

        // 댓글 좋아요 데이터 삭제 확인
        assertThat(member.getLikeComments().contains(comment)).isEqualTo(false);
    }

    @Test
    public void 댓글_수정(){
        CommentUpdateRequestDto requestDto = CommentUpdateRequestDto.builder()
                .commentContent(UPDATE_STR)
                .build();
        CommentResponseDto responseDto = commentService.updateComment(commentPk, requestDto);


        assertThat(responseDto.getCommentContent()).isEqualTo(comment.getCommentContent());
        assertThat(responseDto.getCommentCreatedAt()).isEqualTo(comment.getCommentCreatedAt());
        assertThat(responseDto.getCommentPk()).isEqualTo(comment.getCommentPk());
    }

    @Test
    public void 페이지로_에피소드의_댓글_가져오기(){
        Page<CommentResponseDto> responseDtoPage = commentService.getCommentByEpisode(episodePk, page);
        List<CommentResponseDto> responseDtoList = responseDtoPage.getContent();

        assertThat(responseDtoList.size()).isEqualTo(PAGE_SIZE);
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
        System.out.println(new MemberResponseDto(member));
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
        System.out.println(new EpisodeResponseDto(episode));
    }

    private void setComment(){
        for(int i = 0; i<COUNT; i++){
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