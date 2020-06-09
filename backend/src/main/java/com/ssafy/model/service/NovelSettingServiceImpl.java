package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novelSetting.NovelSettingNoNovelResponseDto;
import com.ssafy.model.dto.novelSetting.NovelSettingResponseDto;
import com.ssafy.model.dto.novelSetting.NovelSettingSaveRequestDto;
import com.ssafy.model.dto.novelSetting.NovelSettingUpdateRequestDto;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.NovelException;
import com.ssafy.model.entity.NovelSetting;
import com.ssafy.model.entity.NovelSettingException;
import com.ssafy.model.repository.NovelRepository;
import com.ssafy.model.repository.NovelSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
public class NovelSettingServiceImpl implements NovelSettingService{
    @Autowired
    NovelRepository novelRepository;
    @Autowired
    NovelSettingRepository novelSettingRepository;

    @Override
    public List<NovelSettingResponseDto> getNovelSettings() {
        List<NovelSetting> novelSettings = novelSettingRepository.findAll();
        List<NovelSettingResponseDto> novelSettingResponseDtos
                = novelSettings.stream().map(novelSetting -> new NovelSettingResponseDto(novelSetting)).collect(Collectors.toList());
        return novelSettingResponseDtos;
    }

    @Override
    public NovelSettingResponseDto getNovelSetting(int novelSettingId) {
        NovelSetting novelSetting = novelSettingRepository.findById(novelSettingId).orElse(null);
        return new NovelSettingResponseDto(novelSetting);
    }

    @Override
    public NovelSettingResponseDto registNovelSetting(NovelSettingSaveRequestDto novelSettingSaveRequestDto){
        Novel novel = novelRepository.findById(novelSettingSaveRequestDto.getNovelPk())
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        NovelSetting novelSetting = novelSettingRepository.save(novelSettingSaveRequestDto.toEntity(novel));
        return new NovelSettingResponseDto(novelSetting);
    }

    @Override
    public NovelSettingResponseDto updateNovelSetting(int novelSettingPk, NovelSettingUpdateRequestDto novelSettingUpdateRequestDto){
        NovelSetting novelSetting = novelSettingRepository.findById(novelSettingPk)
                .orElseThrow(() -> new NovelSettingException(NovelSettingException.NOT_EXIST));
        novelSetting.update(novelSettingUpdateRequestDto);
        novelSettingRepository.save(novelSetting);
        return new NovelSettingResponseDto(novelSetting);
    }

    @Override
    public void deleteNovelSetting(int novelSettingPk) {
        NovelSetting novelSetting = novelSettingRepository.findById(novelSettingPk)
                .orElseThrow(() -> new NovelSettingException(NovelSettingException.NOT_EXIST));
        novelSettingRepository.delete(novelSetting);
    }

    @Override
    public Map<String,Object> getNovelSettingByNovel(int novelPk) {
        Map data = new HashMap();
        Novel novel = novelRepository.findById(novelPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        List<NovelSetting> novelSettings= novelSettingRepository.findByNovel(novel);
        List<NovelSettingNoNovelResponseDto> novelSettingResponseDtos =
                novelSettings.stream().map(novelSetting -> new NovelSettingNoNovelResponseDto(novelSetting)).collect(Collectors.toList());

        data.put("novel", new NovelResponseDto(novel));
        data.put("novelSettings", novelSettingResponseDtos);
        return data;
    }
}
