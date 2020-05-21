package com.ssafy.model.dto.novel;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelUpdateRequestDto{
    private List<String> hashTagStrs;
    private List<Integer> genrePks;
    private String novelName;
    private String novelIntro;
    private String novelImage;
    private boolean novelLimit;
    private boolean novelOpen;
    private int novelStatus;
    private boolean novelOnly;


    @Builder
    public NovelUpdateRequestDto(
            List<Integer> genrePks,
            List<String> hashTagStrs,
            String novelName,
            String novelIntro,
            String novelImage,
            boolean novelLimit,
            boolean novelOpen,
            int novelStatus,
            boolean novelOnly) {

        this.genrePks = genrePks;
        this.hashTagStrs = hashTagStrs;
        this.novelName = novelName;
        this.novelIntro = novelIntro;
        this.novelImage = novelImage;
        this.novelLimit = novelLimit;
        this.novelOpen = novelOpen;
        this.novelStatus = novelStatus;
        this.novelOnly = novelOnly;
    }
}