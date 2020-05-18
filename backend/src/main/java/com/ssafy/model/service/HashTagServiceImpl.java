package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.model.dto.HashTagDTO;
import com.ssafy.model.entity.HashTag;
import com.ssafy.model.repository.HashTagRepository;

public class HashTagServiceImpl implements HashTagService {
	@Autowired
	HashTagRepository hRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public void registHashTag(HashTagDTO hashTag) {
		hRepo.save(modelMapper.map(hashTag, HashTag.class));
	}

	@Override
	public List<HashTagDTO> getHashTags() {
		List<HashTag> results = hRepo.findAll();
		List<HashTagDTO> hashTags = 
				results.stream().map(hashTag -> modelMapper.map(hashTag, HashTagDTO.class))
				.collect(Collectors.toList());
		
		return hashTags;
	}

	@Override
	public List<HashTagDTO> getHashTagsByNovel(int novelPk) {
		// TODO Auto-generated method stub
		return null;
	}

}
