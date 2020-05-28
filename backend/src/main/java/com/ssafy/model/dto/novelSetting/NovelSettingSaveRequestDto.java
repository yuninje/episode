package com.ssafy.model.dto.novelSetting;

import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.NovelSetting;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelSettingSaveRequestDto {
    private int novelPk;
    private String novelSettingName;
    private String novelSettingText;

    @Builder
    public NovelSettingSaveRequestDto(int novelPk, String novelSettingName, String novelSettingText) {
        this.novelPk = novelPk;
        this.novelSettingName = novelSettingName;
        this.novelSettingText = novelSettingText;
    }

    public NovelSetting toEntity(Novel novel){
        return NovelSetting.builder()
                .novel(novel)
                .novelSettingName(novelSettingName)
                .novelSettingText(novelSettingText)
                .build();
    }
}
