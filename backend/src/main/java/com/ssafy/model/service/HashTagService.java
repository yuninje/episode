package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.HashTagDTO;

public interface HashTagService {
	void registHashTag(HashTagDTO hashTag);
	List<HashTagDTO> getHashTags();
	List<HashTagDTO> getHashTagsByNovel(int novelPk);
}
