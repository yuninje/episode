package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.relation.RelationResponseDto;
import com.ssafy.model.dto.relation.RelationSaveRequestDto;

public interface RelationService {
	List<RelationResponseDto> getRelationsByNovel(int novelPk);
	RelationResponseDto getRelation(int characterPk);
	RelationResponseDto registRelation(RelationSaveRequestDto requestDto);
	void deleteRelation(int who, int whonm, boolean type);
}
