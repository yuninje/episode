package com.ssafy.model.dto.novel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}