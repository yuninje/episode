package com.ssafy.model.service;

import com.ssafy.Dummy;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class NovelServiceTest {
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
    public void 소설_페이지_가져오기(){
        Page<NovelResponseDto> responseDtoPage = novelService.getNovels(page, null);

        List<NovelResponseDto> responseDtoList = responseDtoPage.getContent();

        assertThat(responseDtoList.size()).isLessThanOrEqualTo(5);
        assertThat(responseDtoList.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void 소설_하나_가져오기(){
        NovelResponseDto responseDto = novelService.getNovel(novelPk);
        novel = dummy.novelFindById(novelPk);

        assertThat(responseDto.getNovelPk()).isEqualTo(novel.getNovelPk());
        assertThat(responseDto.getNovelName()).isEqualTo(novel.getNovelName());
        assertThat(responseDto.getNovelIntro()).isEqualTo(novel.getNovelIntro());
        assertThat(responseDto.getNovelImage()).isEqualTo(novel.getNovelImage());
        assertThat(responseDto.getNovelLikes()).isEqualTo(novel.getNovelLikes());
        assertThat(responseDto.getNovelStatus()).isEqualTo(novel.getNovelStatus());
        assertThat(responseDto.getNovelUpdatedAt()).isEqualTo(novel.getNovelUpdatedAt());
        assertThat(responseDto.getNovelView()).isEqualTo(novel.getNovelView());
        assertThat(responseDto.getNovelRecommends()).isEqualTo(novel.getNovelRecommends());
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
                .genrePks(genrePks)
                .hashTagStrs(hashTagStrs)
                .build();

        NovelResponseDto responseDto = novelService.registNovel(requestDto);
        novel = dummy.novelFindById(responseDto.getNovelPk());

        assertThat(responseDto.getNovelName()).isEqualTo(novel.getNovelName()).isEqualTo(UPDATE_STR);
        assertThat(responseDto.getNovelIntro()).isEqualTo(novel.getNovelIntro()).isEqualTo(UPDATE_STR);
        assertThat(responseDto.getNovelImage()).isEqualTo(novel.getNovelImage()).isEqualTo(UPDATE_STR);
        assertThat(responseDto.getNovelLikes()).isEqualTo(novel.getNovelLikes());
        assertThat(responseDto.getNovelStatus()).isEqualTo(novel.getNovelStatus());
        assertThat(responseDto.getNovelUpdatedAt()).isEqualTo(novel.getNovelUpdatedAt());
        assertThat(responseDto.getNovelView()).isEqualTo(novel.getNovelView());
        assertThat(responseDto.getNovelRecommends()).isEqualTo(novel.getNovelRecommends());
    }


    @Test
    public void 소설_수정(){
        List<Integer> updateGenrePks = new ArrayList<>();
        updateGenrePks.add(genrePk);
        List<String> updateHashTagStrs = new ArrayList<>();
        updateHashTagStrs.add(hashTagStrs.get(0));
        NovelUpdateRequestDto requestDto = NovelUpdateRequestDto.builder()
                .genrePks(updateGenrePks)
                .hashTagStrs(updateHashTagStrs)
                .novelName(UPDATE_STR)
                .novelIntro(UPDATE_STR)
                .novelImage(UPDATE_STR)
                .novelLimit(false)
                .novelOnly(false)
                .novelOpen(false)
                .novelStatus(1)
                .build();
        NovelResponseDto responseDto = novelService.updateNovel(novelPk, requestDto);
        novel = dummy.novelFindById(responseDto.getNovelPk());

        assertThat(novel.getNovelName()).isEqualTo(UPDATE_STR);
        assertThat(novel.getNovelIntro()).isEqualTo(UPDATE_STR);
        assertThat(novel.getNovelImage()).isEqualTo(UPDATE_STR);
        assertThat(novel.getNovelLimit()).isEqualTo(false);
        assertThat(novel.getNovelOnly()).isEqualTo(false);
        assertThat(novel.getNovelOpen()).isEqualTo(false);
        assertThat(novel.getNovelStatus()).isEqualTo(1);
    }



    @Test
    public void 소설_삭제_성공(){
        novel = dummy.novelFindById(novelPk);

        // 삭제 되어야 할 데이터
        List<Episode> episodes = novel.getEpisodes();

        novelService.deleteNovel(novelPk);

        // novel 이 삭제됬는지 확인
        try {
            dummy.novelFindById(novelPk);
        }catch (NovelException e){
            assertThat(e.getMessage()).isEqualTo(NovelException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);


        // 소설의 에피소드들이 삭제됬는지 확인
        for(Episode episode : episodes){
            catchFlag = false;
            try{
                dummy.episodeFindById(episode.getEpisodePk());
            }catch (EpisodeException e){
                assertThat(e.getMessage()).isEqualTo(EpisodeException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 소설 좋아요 데이터 삭제 확인
        assertThat(member.getLikeNovels().contains(novel)).isEqualTo(false);
        // 소설 & 장르 데이터 삭제 확인
        assertThat(genre.getNovels().contains(novel)).isEqualTo(false);
        // 소설 & 해시태그 데이터 확인
        assertThat(hashTag.getNovels().contains(novel)).isEqualTo(false);
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

        novel = dummy.novelFindById(novelPk);

        assertThat(novel.getGenres().size()).isEqualTo(1);
    }


    @Test
    public void 소설_페이지_회원으로_가져오기_아직안함(){
        assertThat(true).isEqualTo(false);
    }
}