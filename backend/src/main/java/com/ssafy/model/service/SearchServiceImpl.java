package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.SearchDTO;
import com.ssafy.model.entity.Search;
import com.ssafy.model.repository.SearchRepository;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	SearchRepository sRepo;
	@Autowired
	ModelMapper modelMapper;

	public List<SearchDTO> getRealTimeSearch() {
		List<Search> results = sRepo.getRealTimeSearch();
		List<SearchDTO> searchs = 
				results.stream().map(Search -> modelMapper.map(Search, SearchDTO.class))
				.collect(Collectors.toList());
		
		return searchs;
	}
}
