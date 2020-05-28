package com.ssafy.model.dto.novelSetting;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.NovelSetting;
import lombok.ToString;

@ToString
public class NovelSettingNoNovelResponseDto {
    public int novelSettingId;
    public String novelSettingName;
    public String novelSettingText;

    public NovelSettingNoNovelResponseDto(NovelSetting novelSetting){
        this.novelSettingId = novelSetting.getNovelSettingPk();
        this.novelSettingName = novelSetting.getNovelSettingName();
        this.novelSettingText = novelSetting.getNovelSettingText();
    }
}
