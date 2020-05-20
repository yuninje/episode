package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.episode.EpisodeSaveRequestDto;
import com.ssafy.model.dto.episode.EpisodeUpdateRequestDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class) // JUnit에 내장된 Runner 대신 이 클래스를 실행한다.
@SpringBootTest( properties = {"spring.config.location=classpath:application-test.properties"} )
public class EpisodeServiceTest {

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
    private List<Integer> genrePks;

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
        memberService.doLike(memPk, novelPk, NOVEL, true);
        memberService.doLike(memPk, episodePk, EPISODE, true);
        memberService.doLike(memPk, commentPk, COMMENT, true);
        novelService.updateGenreOfNovel(novelPk, genrePks);
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

        episode = episodeFindById(responseDto.getEpisodePk());

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
        Long beforeView = novel.getNovelView();

        EpisodeResponseDto responseDto = episodeService.getEpisode(episodePk);

        assertThat(episode.getEpisodeTitle()).isEqualTo(responseDto.getEpisodeTitle());
        assertThat(episode.getEpisodeContent()).isEqualTo(responseDto.getEpisodeContent());
        assertThat(episode.getEpisodeCreatedAt()).isEqualTo(responseDto.getEpisodeCreatedAt());
        assertThat(episode.getEpisodeView()).isEqualTo(responseDto.getEpisodeView());
        assertThat(episode.getEpisodeWriter()).isEqualTo(responseDto.getEpisodeWriter());

        Long afterView = novel.getNovelView();

        // 소설 조회수 + 1
        assertThat(beforeView+1).isEqualTo(afterView);
    }

    @Test
    public void 에피소드_수정(){
        EpisodeUpdateRequestDto requestDto = EpisodeUpdateRequestDto.builder()
                .episodeTitle(UPDATE_STR)
                .episodeContent(UPDATE_STR)
                .episodeWriter(UPDATE_STR)
                .build();

        EpisodeResponseDto responseDto = episodeService.updateEpisode(episodePk, requestDto);

        episode = episodeFindById(episodePk);

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
            episodeFindById(episodePk);
        }catch (EpisodeException e){
            assertThat(e.getMessage()).isEqualTo(EpisodeException.NOT_EXIST);
            catchFlag = true;
        }
        assertThat(catchFlag).isEqualTo(true);

        // 에피소드의 댓글들 삭제
        for(Comment comment : commentList){
            catchFlag = false;
            try{
                commentFindById(comment.getCommentPk());
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
        genrePks = new ArrayList<>();
        for(int i = 1; i<=COUNT; i++){
            genre = Genre.builder()
                    .genreName(STR+i)
                    .build();
            genre = genreRepository.save(genre);
            genrePk = genre.getGenrePk();
            genrePks.add(genrePk);
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