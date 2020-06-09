package com.ssafy.model.service;

import com.ssafy.model.dto.name.NameResponseDto;
import com.ssafy.model.dto.name.NameSaveRequestDto;
import com.ssafy.model.dto.name.NameUpdateRequestDto;
import com.ssafy.model.entity.Name;

import java.util.List;

public interface NameService {
    List<NameResponseDto> getNames();
    NameResponseDto getName(int namePk);
    NameResponseDto getNameByName(String name);
    Name findOrRegistName(String name);
    NameResponseDto registName(NameSaveRequestDto requestDto);
    NameResponseDto updateName(int namePk, NameUpdateRequestDto requestDto);
    void deleteName(int namePk);
    void deleteName(Name name);
}
