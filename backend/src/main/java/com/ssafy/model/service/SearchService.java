package com.ssafy.model.service;

import com.ssafy.model.dto.search.SearchResponseDto;

import java.util.List;

public interface SearchService {
	List<SearchResponseDto> getRealTimeSearch();
}
