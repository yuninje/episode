package com.ssafy.model.service;

import com.ssafy.model.dto.novelSetting.NovelSettingResponseDto;
import com.ssafy.model.dto.novelSetting.NovelSettingSaveRequestDto;
import com.ssafy.model.dto.novelSetting.NovelSettingUpdateRequestDto;

import java.util.List;
import java.util.Map;

public interface NovelSettingService {
    List<NovelSettingResponseDto> getNovelSettings();
    NovelSettingResponseDto getNovelSetting(int novelSettingPk);
    NovelSettingResponseDto registNovelSetting(NovelSettingSaveRequestDto novelSettingSaveRequestDto);
    NovelSettingResponseDto updateNovelSetting(int novelSettingPk, NovelSettingUpdateRequestDto novelSettingUpdateRequestDto);
    void deleteNovelSetting(int novelSettingPk);
    Map<String,Object> getNovelSettingByNovel(int novelPk);
}
