package com.ssafy.model.service;

import com.ssafy.Dummy;
import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.comment.CommentSaveRequestDto;
import com.ssafy.model.dto.comment.CommentUpdateRequestDto;
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
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class CommentServiceTest {
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
    private List<Episode> episodes;
    private List<Comment> comments;
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
    private Pageable page;

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

        novel = (Novel) data.get("novel");
        novelPk = (int) data.get("novelPk");

        episode = (Episode) data.get("episode");
        episodes = (List) data.get("episodes");
        episodePk = (int) data.get("episodePk");

        comments = (List) data.get("comments");
        comment = (Comment) data.get("comment");
        commentPk = (int) data.get("commentPk");

        genres = (ArrayList) data.get("genres");
        genre = (Genre) data.get("genre");
        genrePks = (ArrayList) data.get("genrePks");
        genrePk = (int) data.get("genrePk");

        hashTags = (ArrayList) data.get("hashTags");
        hashTag = (HashTag) data.get("hashTag");
        hashTagStrs = (ArrayList) data.get("hashTagStrs");
        hashTagPk = (int) data.get("hashTagPk");

        dummy.setRelation();
        catchFlag = false;
    }

    @After
    public void cleanUp(){}

    @Test
    public void 댓글_페이지로_가져오기(){
        page = PageRequest.of(PAGE, PAGE_SIZE, Sort.by("commentCreatedAt").descending());   // 가장 최신이 먼저
        Page<CommentResponseDto> responseDtoPage = commentService.getComments(page);
        List<CommentResponseDto> responseDtoList = responseDtoPage.getContent();

        assertThat(responseDtoList.size()).isEqualTo(PAGE_SIZE);
        assertThat(responseDtoList.get(0).getCommentCreatedAt()).isAfterOrEqualTo(responseDtoList.get(1).getCommentCreatedAt());
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

        comment = dummy.commentFindById(responseDto.getCommentPk());
        assertThat(responseDto.getCommentContent()).isEqualTo(comment.getCommentContent());
    }

    @Test
    public void 댓글_삭제(){
        commentService.deleteComment(commentPk);

        // 삭제한 댓글 확인
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
    public void 페이지로_에피소드의_댓글_가져오기_최신순(){
        page = PageRequest.of(PAGE, PAGE_SIZE, Sort.by("commentCreatedAt").descending());   // 가장 최신이 먼저
        Page<CommentResponseDto> responseDtoPage = commentService.getCommentByEpisode(episodePk, page);
        List<CommentResponseDto> responseDtoList = responseDtoPage.getContent();

        assertThat(responseDtoList.size()).isEqualTo(PAGE_SIZE);
        assertThat(responseDtoList.get(0).getCommentCreatedAt()).isAfterOrEqualTo(responseDtoList.get(1).getCommentCreatedAt());
    }
}