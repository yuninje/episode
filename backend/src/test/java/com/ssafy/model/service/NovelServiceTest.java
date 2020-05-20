package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
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
    private int genrePk;

    private final int COUNT = 5;
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
    public void 소설_페이지_가져오기(){
        Page<NovelResponseDto> responseDtoPage = novelService.getNovels(page, STR);

        List<NovelResponseDto> responseDtoList = responseDtoPage.getContent();

        assertThat(responseDtoList.size()).isLessThanOrEqualTo(5);
        assertThat(responseDtoList.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void 소설_하나_가져오기(){
        NovelResponseDto responseDto = novelService.getNovel(novelPk);
        novel = novelFindById(novelPk);

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


    @Test
    public void 소설_페이지_이름으로_가져오기_아직안함(){
        assertThat(true).isEqualTo(false);
    }


    @Test
    public void 소설_페이지_닉네임으로_가져오기_아직안함(){
        assertThat(true).isEqualTo(false);

    }


    @Test
    public void 소설_페이지_이름또는닉네임으로_가져오기_아직안함(){
        assertThat(true).isEqualTo(false);

    }

    @Test
    public void 소설_추가(){
        NovelSaveRequestDto requestDto = NovelSaveRequestDto.builder()
                .memberPk(memPk)
                .novelName(UPDATE_STR)
                .novelIntro(UPDATE_STR)
                .novelImage(UPDATE_STR)
                .novelLimit(true)
                .novelOnly(true)
                .novelOpen(true)
                .novelStatus(0)
                .build();
        NovelResponseDto responseDto = novelService.registNovel(requestDto);
        novel = novelFindById(responseDto.getNovelPk());

        assertThat(responseDto.getNovelName()).isEqualTo(novel.getNovelName()).isEqualTo(UPDATE_STR);
        assertThat(responseDto.getNovelIntro()).isEqualTo(novel.getNovelIntro()).isEqualTo(UPDATE_STR);
        assertThat(responseDto.getNovelImage()).isEqualTo(novel.getNovelImage()).isEqualTo(UPDATE_STR);
        assertThat(responseDto.getLikes()).isEqualTo(novel.getLikes());
        assertThat(responseDto.getNovelStatus()).isEqualTo(novel.getNovelStatus());
        assertThat(responseDto.getNovelUpdatedAt()).isEqualTo(novel.getNovelUpdatedAt());
        assertThat(responseDto.getNovelView()).isEqualTo(novel.getNovelView());
        assertThat(responseDto.getRecommends()).isEqualTo(novel.getRecommends());
    }


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
        novel = novelFindById(responseDto.getNovelPk());

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



    @Test
    public void 소설_삭제_성공(){
        novel = novelFindById(novelPk);

        // 삭제 되어야 할 데이터
        List<Genre> genres = novel.getGenres();
        List<Episode> episodes = novel.getEpisodes();
        List<Member> likedMembers = novel.getLikedMembers();
        // 해시태그 추가해야함

        novelService.deleteNovel(novelPk);
        try {
            novelFindById(novelPk);
        }catch (NovelException e){
            assertThat(e.getMessage()).isEqualTo(NovelException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);


        // 소설의 에피소드들 삭제
        for(Episode episode : episodes){
            catchFlag = false;
            try{
                episodeFindById(episode.getEpisodePk());
            }catch (EpisodeException e){
                assertThat(e.getMessage()).isEqualTo(EpisodeException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 소설의 장르 데이터 삭제
        for(Genre genre : genres){
            catchFlag = false;
            try{
                genreFindById(genre.getGenrePk());
            }catch (GenreException e){
                assertThat(e.getMessage()).isEqualTo(GenreException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 소설 좋아요 데이터 삭제 확인
        assertThat(member.getLikeNovels().contains(novel)).isEqualTo(false);
    }


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

    @Test
    public void 소설_페이지_장르로_가져오기_아직안함(){
        assertThat(true).isEqualTo(false);
    }

    @Test
    public void 소설의_장르_업데이트(){
        List<Integer> genrePks = new ArrayList<>();
        genrePks.add(genrePk);
        novelService.updateGenreOfNovel(novelPk, genrePks);

        novel = novelFindById(novelPk);

        assertThat(novel.getGenres().size()).isEqualTo(1);
    }


    @Test
    public void 소설_페이지_회원으로_가져오기_아직안함(){
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