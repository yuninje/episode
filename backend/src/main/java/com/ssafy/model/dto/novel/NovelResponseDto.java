package com.ssafy.model.dto.novel;


import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.hashtag.HashTagResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.entity.Novel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelResponseDto {
    private int novelPk;
    private String novelName;
    private String novelIntro;
    private String novelImage;
    private boolean novelLimit;
    private boolean novelOpen;
    private int novelStatus;
    private boolean novelOnly;
    private Long novelView = 0L;
    private LocalDateTime novelUpdatedAt;
    private MemberResponseDto member;
    private List<GenreResponseDto> genres = new ArrayList<>();
    private List<HashTagResponseDto> hashTags = new ArrayList<>();
    private long novelLikes = 0L;
    private long novelRecommends = 0L;

    public NovelResponseDto(Novel novel) {
        this.novelPk = novel.getNovelPk();
        this.novelName = novel.getNovelName();
        this.novelIntro = novel.getNovelIntro();
        this.novelImage = novel.getNovelImage();
        this.novelLimit = novel.getNovelLimit();
        this.novelOpen = novel.getNovelOpen();
        this.novelStatus = novel.getNovelStatus();
        this.novelOnly = novel.getNovelOnly();
        this.novelView = novel.getNovelView();
        this.novelUpdatedAt = novel.getNovelUpdatedAt();
        this.member = new MemberResponseDto(novel.getMember());
        this.genres = novel.getGenres().stream().map(
                genre -> new GenreResponseDto(genre)).collect(Collectors.toList());
        this.hashTags = novel.getHashTags().stream().map(
        		hashTag -> new HashTagResponseDto(hashTag)).collect(Collectors.toList());
        this.novelLikes = novel.getEpisodes().stream().mapToInt(episode -> episode.getLikedMembers().size()).sum();
        this.novelRecommends = novel.getNovelRecommends();
    }

    @Builder
    public NovelResponseDto(int novelPk, String novelName, String novelIntro, String novelImage, boolean novelLimit, boolean novelOpen, int novelStatus, boolean novelOnly, Long novelView, LocalDateTime novelUpdatedAt, MemberResponseDto member, List<GenreResponseDto> genres, long likes, long recommends) {
        this.novelPk = novelPk;
        this.novelName = novelName;
        this.novelIntro = novelIntro;
        this.novelImage = novelImage;
        this.novelLimit = novelLimit;
        this.novelOpen = novelOpen;
        this.novelStatus = novelStatus;
        this.novelOnly = novelOnly;
        this.novelView = novelView;
        this.novelUpdatedAt = novelUpdatedAt;
        this.member = member;
        this.genres = genres;
        this.novelLikes = likes;
        this.novelRecommends = recommends;
    }
}