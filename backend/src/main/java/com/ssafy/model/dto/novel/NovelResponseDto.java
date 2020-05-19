package com.ssafy.model.dto.novel;


import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.entity.Novel;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate novelUpdatedAt;
    private MemberResponseDto member;
    private List<GenreResponseDto> genres = new ArrayList<>();
    private long likes;
    private long recommends;

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
                genreEntity -> new GenreResponseDto(genreEntity)).collect(Collectors.toList());
        this.likes = novel.getEpisodes().stream().mapToInt(episode -> episode.getLikedMembers().size()).sum();
        this.recommends = novel.getRecommends();
    }

    @Builder
    public NovelResponseDto(int novelPk, String novelName, String novelIntro, String novelImage, boolean novelLimit, boolean novelOpen, int novelStatus, boolean novelOnly, Long novelView, LocalDate novelUpdatedAt, MemberResponseDto member, List<GenreResponseDto> genres, long likes, long recommends) {
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
        this.likes = likes;
        this.recommends = recommends;
    }
}