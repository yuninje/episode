package com.ssafy;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.hashtag.HashTagResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.*;
import com.ssafy.model.service.*;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class Dummy {
    @Builder
    public Dummy(MemberRepository memberRepository,
                 NovelRepository novelRepository,
                 EpisodeRepository episodeRepository,
                 GenreRepository genreRepository,
                 CommentRepository commentRepository,
                 SearchRepository searchRepository,
                 HashTagRepository hashTagRepository,
                 MemberService memberService,
                 NovelService novelService,
                 EpisodeService episodeService,
                 CommentService commentService,
                 GenreService genreService,
                 HashTagService hashTagService,
                 SearchService searchService){
        this.memberRepository = memberRepository;
        this.novelRepository = novelRepository;
        this.episodeRepository = episodeRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
        this.searchRepository = searchRepository;
        this.hashTagRepository = hashTagRepository;
        this.memberService = memberService;
        this.novelService = novelService;
        this.episodeService = episodeService;
        this.commentService = commentService;
        this.genreService = genreService;
        this.hashTagService = hashTagService;
        this.searchService = searchService;

        ret = new HashMap<>();

        setMember();
        setGenres();
        setHashTags();
        setNovel();
        setEpisode();
        setComment();
        setRelation();
    }
    public Dummy(int COUNT, int PAGE, int PAGE_SIZE){
        this.COUNT = COUNT;
        this.PAGE = PAGE;
        this.PAGE_SIZE = PAGE_SIZE;
        ret = new HashMap<>();
        page = PageRequest.of(PAGE, PAGE_SIZE);
    }
    private MemberService memberService;
    private NovelService novelService;
    private EpisodeService episodeService;
    private CommentService commentService;
    private GenreService genreService;
    private HashTagService hashTagService;
    private SearchService searchService;

    private MemberRepository memberRepository;
    private NovelRepository novelRepository;
    private EpisodeRepository episodeRepository;
    private CommentRepository commentRepository;
    private GenreRepository genreRepository;
    private SearchRepository searchRepository;
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

    private int COUNT = 10;
    private int PAGE = 0;
    private int PAGE_SIZE = 5;
    private Pageable page;

    private final int MEMBER = 0;
    private final int NOVEL = 1;
    private final int EPISODE = 2;
    private final int COMMENT = 3;

    private final String STR = "STR";
    private final String UPDATE_STR = "UPDATE_STR";


    Map<String, Object> ret;

    public void setMember(){
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
            System.out.println("email : " + member.getMemEmail());
            member = memberRepository.save(member);
            memPk = member.getMemPk();
            System.out.println(new MemberResponseDto(member));
        }

        ret.put("member", member);
        ret.put("memPk", memPk);
    }

    public void setNovel(){
        novel = Novel.builder()
                .member(member)
                .genres(genres)
                .hashTags(hashTags)
                .novelName(STR)
                .novelImage(STR)
                .novelIntro(STR)
                .novelLimit(true)
                .novelOnly(true)
                .novelOpen(true)
                .novelStatus(0)
                .build();
        novelRepository.save(novel);
        novelPk = novel.getNovelPk();
        System.out.println(new NovelResponseDto(novel));

        ret.put("novel", novel);
        ret.put("novelPk", novelPk);
    }

    public void setEpisode(){
        for(int i = 0; i < COUNT; i++){
            String str = i > 0 ? STR+i : STR;
            episode = Episode.builder()
                    .novel(novel)
                    .episodeTitle(str)
                    .episodeContent(str)
                    .episodeCreatedAt(LocalDateTime.now())
                    .episodeView(0L)
                    .episodeWriter(str)
                    .build();

            episode = episodeRepository.save(episode);
            episodePk = episode.getEpisodePk();
            System.out.println(new EpisodeResponseDto(episode));
        }
        ret.put("episode", episode);
        ret.put("episodePk", episodePk);
    }

    public void setComment(){
        for(int i = 0; i<COUNT; i++){
            comment = Comment.builder()
                    .member(member)
                    .episode(episode)
                    .commentContent(STR)
                    .commentCreatedAt(LocalDateTime.now())
                    .build();

            commentRepository.save(comment);
            commentPk = comment.getCommentPk();
            System.out.println(new CommentResponseDto(comment));
        }

        ret.put("comment", comment);
        ret.put("commentPk", commentPk);
    }

    public void setGenres(){
        genrePks = new ArrayList<>();
        genres = new ArrayList<>();
        for(int i = 0; i<COUNT; i++){
            String str = i > 0 ? STR+i : STR;
            genre = Genre.builder()
                    .genreName(str)
                    .build();
            genreRepository.save(genre);
            genrePk = genre.getGenrePk();
            genrePks.add(genrePk);
            genres.add(genre);
            System.out.println(new GenreResponseDto(genre));
        }

        ret.put("genres", genres);
        ret.put("genrePks", genrePks);
        ret.put("genrePk", genrePk);
        ret.put("genre", genre);
    }

    public void setHashTags(){
        hashTags = new ArrayList<>();
        hashTagStrs = new ArrayList<>();
        for(int i = 0; i< COUNT; i++){
            String str = i > 0 ? STR+i : STR;
            hashTag = HashTag.builder()
                    .hashTagName(str)
                    .build();
            hashTagRepository.save(hashTag);
            hashTagPk = hashTag.getHashTagPk();
            hashTagStrs.add(str);
            hashTags.add(hashTag);
            System.out.println(new HashTagResponseDto(hashTag));
        }
        ret.put("hashTags", hashTags);
        ret.put("hashTagStrs", hashTagStrs);
        ret.put("hashTag", hashTag);
        ret.put("hashTagPk", hashTagPk);
    }

    public void setRelation(){
        memberService.doLike(memPk, novelPk, NOVEL, true);
        memberService.doLike(memPk, episodePk, EPISODE, true);
        memberService.doLike(memPk, commentPk, COMMENT, true);
    }
    public Map<String, Object> getRet(){
        return ret;
    }

    public Member memberFindById(int memPk){
        return memberRepository.findById(memPk).orElseThrow(
                () -> new MemberException(MemberException.NOT_EXIST));
    }
    public Novel novelFindById(int novelPk){
        return novelRepository.findById(novelPk).orElseThrow(
                () -> new NovelException(NovelException.NOT_EXIST));
    }
    public Episode episodeFindById(int episodePk){
        return episodeRepository.findById(episodePk).orElseThrow(
                () -> new EpisodeException(EpisodeException.NOT_EXIST));
    }
    public Comment commentFindById(int commentPk){
        return commentRepository.findById(commentPk).orElseThrow(
                () -> new CommentException(CommentException.NOT_EXIST));
    }
    public Genre genreFindById(int genrePk){
        return genreRepository.findById(genrePk).orElseThrow(
                () -> new GenreException(GenreException.NOT_EXIST));
    }
    public HashTag hashTagFindById(int hashTagPk){
        return hashTagRepository.findById(hashTagPk).orElseThrow(
                () -> new HashTagException(HashTagException.NOT_EXIST));
    }
}
