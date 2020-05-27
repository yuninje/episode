package com.ssafy.model.dto.novelSetting;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.NovelSetting;
import lombok.ToString;

@ToString
public class NovelSettingResponseDto {
    public NovelResponseDto novel;
    public int novelSettingId;
    public String novelSettingName;
    public String novelSettingText;

    public NovelSettingResponseDto(NovelSetting novelSetting){
        this.novel = new NovelResponseDto(novelSetting.getNovel());
        this.novelSettingId = novelSetting.getNovelSettingPk();
        this.novelSettingName = novelSetting.getNovelSettingName();
        this.novelSettingText = novelSetting.getNovelSettingText();
    }
}
