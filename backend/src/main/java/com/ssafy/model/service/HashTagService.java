package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.HashTagDTO;

public interface HashTagService {
	List<HashTagDTO> getHashTags();
	List<HashTagDTO> getHashTagsByNovel(int novelPk);
	HashTagDTO getHashTag(int genrePk);
	void registHashTag(HashTagDTO hashTag);
	void deleteHashTag(int hashTagPk);
}
