package com.ssafy.model.repository;

import java.util.List;

import com.ssafy.model.entity.Search;

public interface SearchCustomRepository {
	List<Search> getRealTimeSearch();
}
