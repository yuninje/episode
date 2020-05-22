package com.ssafy.model.service;

import com.ssafy.Dummy;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.episode.EpisodeSaveRequestDto;
import com.ssafy.model.dto.episode.EpisodeUpdateRequestDto;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class EpisodeServiceTest {
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
    public void 에피소드_생성(){  // 소설 updatedAt 갱신
        LocalDateTime beforeTime = novel.getNovelUpdatedAt();

        EpisodeSaveRequestDto requestDto = EpisodeSaveRequestDto.builder()
                .episodeTitle(UPDATE_STR)
                .episodeContent(UPDATE_STR)
                .episodeWriter(UPDATE_STR)
                .novelPk(novelPk)
                .build();
        EpisodeResponseDto responseDto = episodeService.registEpisode(requestDto);

        episode = dummy.episodeFindById(responseDto.getEpisodePk());

        assertThat(episode.getEpisodeTitle()).isEqualTo(responseDto.getEpisodeTitle());
        assertThat(episode.getEpisodeContent()).isEqualTo(responseDto.getEpisodeContent());
        assertThat(episode.getEpisodeCreatedAt()).isEqualTo(responseDto.getEpisodeCreatedAt());
        assertThat(episode.getEpisodeView()).isEqualTo(responseDto.getEpisodeView());
        assertThat(episode.getEpisodeWriter()).isEqualTo(responseDto.getEpisodeWriter());

        LocalDateTime afterTime = novel.getNovelUpdatedAt();
        assertThat(beforeTime).isNotEqualTo(afterTime);
    }

    @Test
    public void 에피소드_페이지_가져오기(){
        Page<EpisodeResponseDto> responseDtoPage = episodeService.getEpisodes(page);

        List<EpisodeResponseDto> responseDtoList = responseDtoPage.getContent();
        assertThat(responseDtoList.size()).isEqualTo(PAGE_SIZE);
    }

    @Test
    public void 에피소드_하나_가져오기(){
        Long beforeNovelView = novel.getNovelView();
        Long beforeEpisodeView = episode.getEpisodeView();
        EpisodeResponseDto responseDto = episodeService.getEpisode(episodePk);

        assertThat(episode.getEpisodeTitle()).isEqualTo(responseDto.getEpisodeTitle());
        assertThat(episode.getEpisodeContent()).isEqualTo(responseDto.getEpisodeContent());
        assertThat(episode.getEpisodeCreatedAt()).isEqualTo(responseDto.getEpisodeCreatedAt());
        assertThat(episode.getEpisodeView()).isEqualTo(responseDto.getEpisodeView());
        assertThat(episode.getEpisodeWriter()).isEqualTo(responseDto.getEpisodeWriter());

        Long afterNovelView = novel.getNovelView();
        Long afterEpisodeView = episode.getEpisodeView();

        // 소설 조회수 + 1
        assertThat(beforeNovelView+1).isEqualTo(afterNovelView);
        // 에피소드 조회수 + 1
        assertThat(beforeEpisodeView+1).isEqualTo(afterEpisodeView);
    }

    @Test
    public void 에피소드_수정(){
        EpisodeUpdateRequestDto requestDto = EpisodeUpdateRequestDto.builder()
                .episodeTitle(UPDATE_STR)
                .episodeContent(UPDATE_STR)
                .episodeWriter(UPDATE_STR)
                .build();

        EpisodeResponseDto responseDto = episodeService.updateEpisode(episodePk, requestDto);

        episode = dummy.episodeFindById(episodePk);

        assertThat(episode.getEpisodeTitle()).isEqualTo(responseDto.getEpisodeTitle()).isEqualTo(UPDATE_STR);
        assertThat(episode.getEpisodeContent()).isEqualTo(responseDto.getEpisodeContent()).isEqualTo(UPDATE_STR);
        assertThat(episode.getEpisodeCreatedAt()).isEqualTo(responseDto.getEpisodeCreatedAt());
        assertThat(episode.getEpisodeView()).isEqualTo(responseDto.getEpisodeView());
        assertThat(episode.getEpisodeWriter()).isEqualTo(responseDto.getEpisodeWriter()).isEqualTo(UPDATE_STR);
    }

    
    @Test
    public void 에피소드_삭제(){
        List<Comment> commentList = episode.getComments();
        List<Member> likedMembers = episode.getLikedMembers();

        episodeService.deleteEpisode(episodePk);
        try {
            dummy.episodeFindById(episodePk);
        }catch (EpisodeException e){
            assertThat(e.getMessage()).isEqualTo(EpisodeException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);

        // 에피소드의 댓글들 삭제
        for(Comment comment : commentList){
            catchFlag = false;
            try{
                dummy.commentFindById(comment.getCommentPk());
            }catch (CommentException e){
                assertThat(e.getMessage()).isEqualTo(CommentException.NOT_EXIST);
                catchFlag = true;
            }
            assertThat(catchFlag).isEqualTo(true);
        }

        // 에피소드의 좋아요 데이터 삭제
        assertThat(member.getLikeEpisodes().contains(episode)).isEqualTo(false);
    }

    @Test
    public void 에피소드_소설로_가져오기_아직안함(){
        assertThat(false).isEqualTo(true);
    }

}