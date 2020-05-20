package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class NovelServiceTest {

    @Autowired
    private NovelService novelService;

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
    private List<Integer> genrePks;

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
        setGenres();
        catchFlag = false;
    }

    @Transactional
    @Test
    public void 소설_페이지_가져오기(){
        Page<NovelResponseDto> responseDtoPage = novelService.getNovels(page, STR);

        assertThat(responseDtoPage.getTotalPages()).isLessThanOrEqualTo(5);
        assertThat(responseDtoPage.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Transactional
    @Test
    public void 소설_하나_가져오기(){
        NovelResponseDto responseDto = novelService.getNovel(novelPk);
        Novel novel = novelFindById(novelPk);

        assertThat(responseDto.getNovelPk()).isEqualTo(novel.getNovelPk());
        assertThat(responseDto.getNovelName()).isEqualTo(novel.getNovelName());
        assertThat(responseDto.getNovelIntro()).isEqualTo(novel.getNovelIntro());
        assertThat(responseDto.getNovelImage()).isEqualTo(novel.getNovelImage());
        assertThat(responseDto.getLikes()).isEqualTo(novel.getLikes());
        assertThat(responseDto.getNovelStatus()).isEqualTo(novel.getNovelStatus());
        assertThat(responseDto.getNovelUpdatedAt()).isEqualTo(novel.getNovelUpdatedAt());
        assertThat(responseDto.getNovelView()).isEqualTo(novel.getNovelView());
        assertThat(responseDto.getRecommends()).isEqualTo(novel.getRecommends());
    }

    @Transactional
    @Test
    public void 소설_이름으로_가져오기(){

    }

    @Transactional
    @Test
    public void 소설_닉네임으로_가져오기(){

    }

    @Transactional
    @Test
    public void 소설_이름_또는_닉네임으로_가져오기(){

    }

    @Transactional
    @Test
    public void 소설_추가(){
        NovelSaveRequestDto requestDto = NovelSaveRequestDto.builder()
                .memberPk(memPk)
                .novelName(STR)
                .novelIntro(STR)
                .novelImage(STR)
                .novelLimit(true)
                .novelOnly(true)
                .novelOpen(true)
                .novelStatus(0)
                .build();
        NovelResponseDto responseDto = novelService.registNovel(requestDto);
        Novel novel = novelFindById(responseDto.getNovelPk());

        assertThat(responseDto.getNovelPk()).isEqualTo(novel.getNovelPk());
        assertThat(responseDto.getNovelName()).isEqualTo(novel.getNovelName());
        assertThat(responseDto.getNovelIntro()).isEqualTo(novel.getNovelIntro());
        assertThat(responseDto.getNovelImage()).isEqualTo(novel.getNovelImage());
        assertThat(responseDto.getLikes()).isEqualTo(novel.getLikes());
        assertThat(responseDto.getNovelStatus()).isEqualTo(novel.getNovelStatus());
        assertThat(responseDto.getNovelUpdatedAt()).isEqualTo(novel.getNovelUpdatedAt());
        assertThat(responseDto.getNovelView()).isEqualTo(novel.getNovelView());
        assertThat(responseDto.getRecommends()).isEqualTo(novel.getRecommends());
    }

    @Transactional
    @Test
    public void 소설_수정(){
        NovelUpdateRequestDto requestDto = NovelUpdateRequestDto.builder()
                .novelName(UPDATE_STR)
                .novelIntro(UPDATE_STR)
                .novelImage(UPDATE_STR)
                .novelLimit(false)
                .novelOnly(false)
                .novelOpen(false)
                .novelStatus(1)
                .build();
        NovelResponseDto responseDto = novelService.updateNovel(novelPk, requestDto);
        Novel novel = novelFindById(responseDto.getNovelPk());

        assertThat(responseDto.getNovelPk()).isEqualTo(novel.getNovelPk());
        assertThat(responseDto.getNovelName()).isEqualTo(novel.getNovelName());
        assertThat(responseDto.getNovelIntro()).isEqualTo(novel.getNovelIntro());
        assertThat(responseDto.getNovelImage()).isEqualTo(novel.getNovelImage());
        assertThat(responseDto.getLikes()).isEqualTo(novel.getLikes());
        assertThat(responseDto.getNovelStatus()).isEqualTo(novel.getNovelStatus());
        assertThat(responseDto.getNovelUpdatedAt()).isEqualTo(novel.getNovelUpdatedAt());
        assertThat(responseDto.getNovelView()).isEqualTo(novel.getNovelView());
        assertThat(responseDto.getRecommends()).isEqualTo(novel.getRecommends());
    }


    @Transactional
    @Test
    public void 소설_삭제_성공(){
        novelService.deleteNovel(novelPk);
        try {
            novelFindById(novelPk);
        }catch (NovelException e){
            assertThat(e.getMessage()).isEqualTo(NovelException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);
    }

    @Transactional
    @Test
    public void 소설_삭제_실패(){
        try {
            novelService.deleteNovel(novelPk+1); //
        }catch (NovelException e){
            assertThat(e.getMessage()).isEqualTo(NovelException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);
    }

    @Transactional
    @Test
    public void 소설_장르로_가져오기_아직_안함(){
        assertThat(true).isEqualTo(false);
    }
    @Transactional
    @Test
    public void 소설의_장르_업데이트(){
        novelService.updateGenreOfNovel(novelPk, genrePks);

        Novel novel = novelFindById(novelPk);

        assertThat(novel.getGenres().size()).isEqualTo(GENRE_COUNT);
    }

    @Transactional
    @Test
    public void 회원의_소설_페이지_가져오기_아직안함(){
        assertThat(true).isEqualTo(false);
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

    private void setGenres(){
        genrePks = new ArrayList<>();
        for(int i = 0; i < GENRE_COUNT; i++){
            genre = Genre.builder()
                    .genreName(STR+i)
                    .build();
            genre = genreRepository.save(genre);
            genrePks.add(genre.getGenrePk());
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
}