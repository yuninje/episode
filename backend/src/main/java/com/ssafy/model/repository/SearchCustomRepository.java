package com.ssafy.model.repository;

import com.ssafy.model.entity.Search;

import java.util.List;

public interface SearchCustomRepository {
	List<Search> getRealTimeSearch();
}
