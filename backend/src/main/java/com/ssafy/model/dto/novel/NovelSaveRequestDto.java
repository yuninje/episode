package com.ssafy.model.dto.novel;

import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.HashTag;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelSaveRequestDto{
    private int memberPk;
    private String novelName;
    private String novelIntro;
    private String novelImage;
    private boolean novelLimit;
    private boolean novelOpen;
    private int novelStatus;
    private boolean novelOnly;
    private List<Integer> genrePks;
    private List<String> hashTagStrs;

    @Builder
    public NovelSaveRequestDto(int memberPk, String novelName, String novelIntro, String novelImage, boolean novelLimit, boolean novelOpen, int novelStatus, boolean novelOnly, List<Integer> genrePks, List<String> hashTagStrs) {
        this.memberPk = memberPk;
        this.novelName = novelName;
        this.novelIntro = novelIntro;
        this.novelImage = novelImage;
        this.novelLimit = novelLimit;
        this.novelOpen = novelOpen;
        this.novelStatus = novelStatus;
        this.novelOnly = novelOnly;
        this.genrePks = genrePks;
        this.hashTagStrs = hashTagStrs;
    }

    public Novel toEntity(Member member, List<Genre> genres, List<HashTag> hashTags){

        return Novel.builder()
                .member(member)
                .hashTags(hashTags)
                .genres(genres)
                .novelName(novelName)
                .novelIntro(novelIntro)
                .novelImage(novelImage)
                .novelLimit(novelLimit)
                .novelOpen(novelOpen)
                .novelStatus(novelStatus)
                .novelOnly(novelOnly)
                .build();
    }
}