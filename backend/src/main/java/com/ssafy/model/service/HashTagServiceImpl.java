package com.ssafy.model.service;

import com.ssafy.model.dto.hashtag.HashTagResponseDto;
import com.ssafy.model.dto.hashtag.HashTagSaveRequestDto;
import com.ssafy.model.entity.HashTag;
import com.ssafy.model.repository.HashTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
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
		hRepo.save(requestDto.toEntity());
	}
	
	@Override
	public void deleteHashTag(int hashTagPk) {
		hRepo.deleteById(hashTagPk);
	}

	public HashTag findOrRegistHashTag(String hashTagName){
		HashTag hashTag = hRepo.findByHashTagName(hashTagName);
		if(hashTag == null){
			hashTag = HashTag.builder().hashTagName(hashTagName).build();
			hRepo.save(hashTag);
			System.out.println("findOrRegistHashTag - pk : " + hashTag.getHashTagPk());
		}
		return hashTag;
	}
}
