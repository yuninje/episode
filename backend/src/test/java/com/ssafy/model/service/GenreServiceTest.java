package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.genre.GenreSaveRequestDto;
import com.ssafy.model.dto.genre.GenreUpdateRequestDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class GenreServiceTest {

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

    private final int GENRE_COUNT = 5;
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
        System.out.println("setUp()");
        memberRepository.deleteAll();
        novelRepository.deleteAll();
        episodeRepository.deleteAll();
        commentRepository.deleteAll();
        genreRepository.deleteAll();
        setMember();
        setNovel();
        setEpisode();
        setComment();
        setGenre();
        catchFlag = false;
    }

    @Test
    public void 장르_하나_가져오기(){
        GenreResponseDto responseDto = genreService.getGenre(genrePk);

        Genre genre = genreFindById(genrePk);

        assertThat(responseDto.getGenreName()).isEqualTo(genre.getGenreName()).isEqualTo(STR+4);
    }
    @Test
    public void 장르_전체_가져오기(){
        List<GenreResponseDto> responseDtoList = genreService.getGenres();

        assertThat(responseDtoList.size()).isEqualTo(5);
    }
    @Test
    public void 장르_추가(){
        GenreSaveRequestDto requestDto = GenreSaveRequestDto.builder()
                .genreName(UPDATE_STR)
                .build();
        GenreResponseDto responseDto = genreService.registGenre(requestDto);

        Genre genre = genreFindById(responseDto.getGenrePk());
        assertThat(genre.getGenreName()).isEqualTo(UPDATE_STR);
    }
    
    @Transactional
    @Test
    public void 장르_삭제(){
        Genre genre = genreFindById(genrePk);

        // 삭제 되어야 할 데이터
        List<Novel> novels = genre.getNovels();

        genreService.deleteGenre(genrePk);
        try{
            genreRepository.findById(genrePk)
                    .orElseThrow(() -> new GenreException(GenreException.NOT_EXIST));
        }catch (GenreException e){
            catchFlag = true;
            assertThat(e.getMessage()).isEqualTo(GenreException.NOT_EXIST);
        }
        assertThat(catchFlag).isEqualTo(true);

        // 에피소드의 댓글들 삭제
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
    }

    @Test
    public void 장르_수정(){
        GenreUpdateRequestDto requestDto = GenreUpdateRequestDto.builder()
                .genreName(UPDATE_STR)
                .build();

        GenreResponseDto responseDto = genreService.updateGenre(genrePk, requestDto);
        Genre genre = genreRepository.findById(genrePk)
                .orElseThrow(() -> new GenreException(GenreException.NOT_EXIST));

        assertThat(genre.getGenreName()).isEqualTo(responseDto.getGenreName()).isEqualTo(UPDATE_STR);
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

    private void setGenre(){
        for(int i = 0; i<GENRE_COUNT; i++){
            genre = Genre.builder()
                    .genreName(STR+i)
                    .build();
            genre = genreRepository.save(genre);
            genrePk = genre.getGenrePk();
            System.out.println("setGenre() - genre : " + new GenreResponseDto(genre));
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