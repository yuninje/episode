package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.relation.RelationResponseDto;
import com.ssafy.model.dto.relation.RelationSaveRequestDto;
import com.ssafy.model.dto.relation.RelationUpdateRequestDto;

public interface RelationService {
	List<RelationResponseDto> getRelations();
	RelationResponseDto getRelation(int relationPk);
	List<RelationResponseDto> getRelationsByNovel(int novelPk);
	RelationResponseDto registRelation(RelationSaveRequestDto requestDto);
	RelationResponseDto updateRelation(int relationPk, RelationUpdateRequestDto requestDto);
	void deleteRelation(int who, int whom);
}
