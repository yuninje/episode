package com.ssafy.model.service;

import com.ssafy.model.dto.search.SearchResponseDto;
import com.ssafy.model.entity.Search;
import com.ssafy.model.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	SearchRepository sRepo;

	public List<SearchResponseDto> getRealTimeSearch() {
		List<Search> results = sRepo.getRealTimeSearch();
		List<SearchResponseDto> searchs =
				results.stream().map(search -> new SearchResponseDto(search)).collect(Collectors.toList());

		return searchs;
	}
}
