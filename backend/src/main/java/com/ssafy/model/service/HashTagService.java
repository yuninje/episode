package com.ssafy.model.service;

import com.ssafy.model.dto.hashtag.HashTagResponseDto;
import com.ssafy.model.dto.hashtag.HashTagSaveRequestDto;

import java.util.List;

public interface HashTagService {
	List<HashTagResponseDto> getHashTags();
	List<HashTagResponseDto> getHashTagsByNovel(int novelPk);
	HashTagResponseDto getHashTag(int genrePk);
	void registHashTag(HashTagSaveRequestDto requestDto);
	void deleteHashTag(int hashTagPk);
}
