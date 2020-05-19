package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.HashTagDTO;
import com.ssafy.model.entity.HashTag;
import com.ssafy.model.repository.HashTagRepository;

@Service
public class HashTagServiceImpl implements HashTagService {
	@Autowired
	HashTagRepository hRepo;
	@Autowired
	ModelMapper modelMapper;
	
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
	
	@Override
	public HashTagDTO getHashTag(int hashTagPk) {
		HashTag hashTag = hRepo.findById(hashTagPk).orElse(null);
		HashTagDTO hashTagDTO = modelMapper.map(hashTag, HashTagDTO.class);
		
		return hashTagDTO;
	}
	
	@Override
	public void registHashTag(HashTagDTO hashTag) {
		hRepo.save(modelMapper.map(hashTag, HashTag.class));
	}
	
	@Override
	public void deleteHashTag(int hashTagPk) {
		hRepo.deleteById(hashTagPk);
	}
}
