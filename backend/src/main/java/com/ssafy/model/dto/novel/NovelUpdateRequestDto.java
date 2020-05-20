package com.ssafy.model.dto.novel;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelUpdateRequestDto{
    private String novelName;
    private String novelIntro;
    private String novelImage;
    private boolean novelLimit;
    private boolean novelOpen;
    private int novelStatus;
    private boolean novelOnly;


    @Builder
    public NovelUpdateRequestDto(
            String novelName,
            String novelIntro,
            String novelImage,
            boolean novelLimit,
            boolean novelOpen,
            int novelStatus,
            boolean novelOnly) {

        this.novelName = novelName;
        this.novelIntro = novelIntro;
        this.novelImage = novelImage;
        this.novelLimit = novelLimit;
        this.novelOpen = novelOpen;
        this.novelStatus = novelStatus;
        this.novelOnly = novelOnly;
    }
}