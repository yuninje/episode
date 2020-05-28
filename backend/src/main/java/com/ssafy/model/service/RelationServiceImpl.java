package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.character.CharacterResponseNoNovelDto;
import com.ssafy.model.dto.relation.RelationResponseDto;
import com.ssafy.model.dto.relation.RelationSaveRequestDto;
import com.ssafy.model.entity.Character;
import com.ssafy.model.entity.CharacterException;
import com.ssafy.model.entity.Relation;
import com.ssafy.model.repository.CharacterRepository;
import com.ssafy.model.repository.RelationRepository;

@Service
public class RelationServiceImpl implements RelationService {
	@Autowired
	RelationRepository rRepo;
	@Autowired
	CharacterRepository cRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<RelationResponseDto> getRelationsByNovel(int novelPk) {
		List<Relation> relationEntityList = rRepo.findByNovel(novelPk);
		List<RelationResponseDto> relations = 
				relationEntityList.stream().map(
						relationEntity -> new RelationResponseDto(relationEntity)).collect(Collectors.toList());
		
		return relations;
	}

	@Override
	public RelationResponseDto getRelation(int characterPk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationResponseDto registRelation(RelationSaveRequestDto requestDto) {
		Character who = cRepo.findById(requestDto.getWho())
				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));
		
		Character whom = cRepo.findById(requestDto.getWhom())
				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));
		
		Relation relationEntity = requestDto.toEntity(who, whom);
		relationEntity = rRepo.save(relationEntity);
		
		RelationResponseDto relation = new RelationResponseDto(relationEntity);
		
		return relation;
	}

	@Override
	public void deleteRelation(int who, int whonm, boolean type) {
		// TODO Auto-generated method stub

	}

}
