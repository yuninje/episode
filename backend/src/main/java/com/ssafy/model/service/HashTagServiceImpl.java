package com.ssafy.model.service;

import com.ssafy.model.dto.hashtag.HashTagResponseDto;
import com.ssafy.model.dto.hashtag.HashTagSaveRequestDto;
import com.ssafy.model.entity.HashTag;
import com.ssafy.model.repository.HashTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashTagServiceImpl implements HashTagService {
	@Autowired
	HashTagRepository hRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<HashTagResponseDto> getHashTags() {
		List<HashTag> results = hRepo.findAll();
		List<HashTagResponseDto> hashTags =
				results.stream().map(hashTag -> new HashTagResponseDto(hashTag)).collect(Collectors.toList());
		
		return hashTags;
	}
	
	@Override
	public List<HashTagResponseDto> getHashTagsByNovel(int novelPk) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HashTagResponseDto getHashTag(int hashTagPk) {
		HashTag hashTag = hRepo.findById(hashTagPk).orElse(null);

		HashTagResponseDto responseDto = new HashTagResponseDto(hashTag);
		
		return responseDto;
	}
	
	@Override
	public void registHashTag(HashTagSaveRequestDto requestDto) {
		hRepo.save(modelMapper.map(requestDto, HashTag.class));
	}
	
	@Override
	public void deleteHashTag(int hashTagPk) {
		hRepo.deleteById(hashTagPk);
	}
}
