package com.ssafy.model.service;

import com.ssafy.model.dto.name.NameResponseDto;
import com.ssafy.model.dto.name.NameSaveRequestDto;
import com.ssafy.model.dto.name.NameUpdateRequestDto;
import com.ssafy.model.entity.Name;
import com.ssafy.model.entity.NovelException;
import com.ssafy.model.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class NameServiceImpl implements NameService {

    @Autowired
    NameRepository nameRepository;

    @Override
    public List<NameResponseDto> getNames() {
        List<Name> names = nameRepository.findAll();
        List<NameResponseDto> responseDtoList = names.stream().map(name -> new NameResponseDto(name)).collect(Collectors.toList());
        return responseDtoList;
    }

    @Override
    public NameResponseDto getName(int namePk) {
        Name name = nameRepository.findById(namePk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        NameResponseDto nameResponseDto = new NameResponseDto(name);
        return nameResponseDto;
    }

    @Override
    public NameResponseDto getNameByName(String requstName) {
        // 해당 이름이 존재하지 않으면 디비에 데이터 생성 후 리턴
        Name name = findOrRegistName(requstName);
        NameResponseDto nameResponseDto = new NameResponseDto(name);
        return nameResponseDto;
    }

    @Override
    public Name findOrRegistName(String name){
        return nameRepository.findByNameName(name)
                .orElseGet(() ->
                        nameRepository.save(Name.builder().nameName(name).build()));
    }

    @Override
    public NameResponseDto registName(NameSaveRequestDto requestDto) {
        Name name = nameRepository.save(requestDto.toEntity());
        NameResponseDto nameResponseDto = new NameResponseDto(name);
        return nameResponseDto;
    }

    @Override
    public NameResponseDto updateName(int namePk, NameUpdateRequestDto requestDto) {    // 사용할 일이 있을까 싶음
        Name name = nameRepository.findById(namePk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        name.update(requestDto.getName());
        nameRepository.save(name);

        NameResponseDto nameResponseDto = new NameResponseDto(name);
        return nameResponseDto;
    }

    @Override
    public void deleteName(int namePk) {
        Name name = nameRepository.findById(namePk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        deleteName(name);
    }

    @Override
    public void deleteName(Name name) {
        name.beforeDelete();
        nameRepository.save(name);
        nameRepository.delete(name);
    }
}
