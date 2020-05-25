package com.ssafy.model.service;

import com.ssafy.Dummy;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.genre.GenreSaveRequestDto;
import com.ssafy.model.dto.genre.GenreUpdateRequestDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class GenreServiceTest {
    Dummy dummy;
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
    private HashTagService hashTagService;
    @Autowired
    private SearchService searchService;

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
    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private HashTagRepository hashTagRepository;

    private Member member;
    private Novel novel;
    private Episode episode;
    private Comment comment;
    private Genre genre;
    private HashTag hashTag;
    private List<Genre> genres;
    private List<HashTag> hashTags;

    private int memPk;
    private int novelPk;
    private int episodePk;
    private int commentPk;
    private int genrePk;
    private int hashTagPk;
    private List<Integer> genrePks;
    private List<String> hashTagStrs;

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
        dummy = Dummy.builder()
                .memberRepository(memberRepository)
                .novelRepository(novelRepository)
                .episodeRepository(episodeRepository)
                .genreRepository(genreRepository)
                .commentRepository(commentRepository)
                .searchRepository(searchRepository)
                .hashTagRepository(hashTagRepository)
                .memberService(memberService)
                .novelService(novelService)
                .episodeService(episodeService)
                .commentService(commentService)
                .genreService(genreService)
                .hashTagService(hashTagService)
                .searchService(searchService)
                .build();
        Map<String, Object> data = dummy.getRet();

        member = (Member) data.get("member");
        memPk = (int) data.get("memPk");

        novelPk = (int) data.get("novelPk");
        novel = (Novel) data.get("novel");

        episode = (Episode) data.get("episode");
        episodePk = (int) data.get("episodePk");

        comment = (Comment) data.get("comment");
        commentPk = (int) data.get("commentPk");

        genres = (ArrayList) data.get("genres");
        genrePks = (ArrayList) data.get("genrePks");
        genre = (Genre) data.get("genre");
        genrePk = (int) data.get("genrePk");

        hashTag = (HashTag) data.get("hashTag");
        hashTagStrs = (ArrayList) data.get("hashTagStrs");
        hashTags = (ArrayList) data.get("hashTags");
        hashTagPk = (int) data.get("hashTagPk");

        dummy.setRelation();
        catchFlag = false;
    }
    @After
    public void cleanUp(){}

    @Test
    public void 장르_하나_가져오기(){
        GenreResponseDto responseDto = genreService.getGenre(genrePk);

        genre = dummy.genreFindById(genrePk);

        assertThat(responseDto.getGenreName()).isEqualTo(genre.getGenreName()).isEqualTo(STR+(COUNT-1));
    }
    @Test
    public void 장르_전체_가져오기(){
        List<GenreResponseDto> responseDtoList = genreService.getGenres();

        assertThat(responseDtoList.size()).isEqualTo(COUNT);
    }
    @Test
    public void 장르_추가(){
        GenreSaveRequestDto requestDto = GenreSaveRequestDto.builder()
                .genreName(UPDATE_STR)
                .build();
        GenreResponseDto responseDto = genreService.registGenre(requestDto);

        genre = dummy.genreFindById(responseDto.getGenrePk());
        assertThat(genre.getGenreName()).isEqualTo(UPDATE_STR);
    }


    @Test
    public void 장르_삭제(){
        genre = dummy.genreFindById(genrePk);

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

        // 장르에 속한 소설_장르 데이터 삭제
        assertThat(novel.getGenres().contains(genre)).isEqualTo(false);
    }

    @Test
    public void 장르_수정(){
        GenreUpdateRequestDto requestDto = GenreUpdateRequestDto.builder()
                .genreName(UPDATE_STR)
                .build();

        GenreResponseDto responseDto = genreService.updateGenre(genrePk, requestDto);
        genre = genreRepository.findById(genrePk)
                .orElseThrow(() -> new GenreException(GenreException.NOT_EXIST));

        assertThat(genre.getGenreName()).isEqualTo(responseDto.getGenreName()).isEqualTo(UPDATE_STR);
    }


    private void setMember(){
        for(int i = 0; i<COUNT; i++){
            String str = i > 0 ? STR+i : STR;
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
}