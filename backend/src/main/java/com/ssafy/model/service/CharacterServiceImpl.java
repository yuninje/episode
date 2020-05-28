package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.character.CharacterResponseDto;
import com.ssafy.model.dto.character.CharacterResponseNoNovelDto;
import com.ssafy.model.dto.character.CharacterSaveRequestDto;
import com.ssafy.model.dto.character.CharacterUpdateRequestDto;
import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.entity.Character;
import com.ssafy.model.entity.CharacterException;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.NovelException;
import com.ssafy.model.entity.Relation;
import com.ssafy.model.repository.CharacterRepository;
import com.ssafy.model.repository.NovelRepository;

@Service
public class CharacterServiceImpl implements CharacterService {
	@Autowired 
	NovelRepository nRepo;
	@Autowired
	CharacterRepository cRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<CharacterResponseNoNovelDto> getCharacters() {
		List<Character> characterEntityList = cRepo.findAll();
		List<CharacterResponseNoNovelDto> characters
			= characterEntityList.stream().map(
					characterEntity -> new CharacterResponseNoNovelDto(characterEntity)).collect(Collectors.toList());
			
		return characters;
	}

	@Override
	public List<CharacterResponseNoNovelDto> getCharactersByNovel(int novelPk) {
		List<Character> characterEntityList = cRepo.findByNovelPk(novelPk);
		List<CharacterResponseNoNovelDto> characters
			= characterEntityList.stream().map(
					characterEntity -> new CharacterResponseNoNovelDto(characterEntity)).collect(Collectors.toList());
			
		return characters;
	}

	@Override
	public CharacterResponseNoNovelDto getCharacter(int characterPk) {
		Character characterEntity = cRepo.findById(characterPk)
				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));
		
		CharacterResponseNoNovelDto character = new CharacterResponseNoNovelDto(characterEntity);
		
		return character;
	}

	@Override
	public CharacterResponseNoNovelDto registCharacter(CharacterSaveRequestDto requestDto) {
		Novel novel = nRepo.findById(requestDto.getNovelPk())
				.orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
		
		Character characterEntity = requestDto.toEntity(novel);
		characterEntity = cRepo.save(characterEntity);
		
		CharacterResponseNoNovelDto character = new CharacterResponseNoNovelDto(characterEntity);
		
		return character;
	}
	
	@Override
	public CharacterResponseNoNovelDto updateCharacter(int characterPk, CharacterUpdateRequestDto requestDto) {
		Character characterEntity = cRepo.findById(characterPk)
				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));
		
		characterEntity.update(
				requestDto.getCharacterName(), 
				requestDto.getCharacterAge(), 
				requestDto.isCharacterGender(), 
				requestDto.getCharacterRole(), 
				requestDto.getCharacterJob(), 
				requestDto.getCharacterPersonallity(), 
				requestDto.getCharacterSignificant(), 
				requestDto.getCharacterMore(), 
				requestDto.getCharacterImage()
		);
		
		CharacterResponseNoNovelDto character = new CharacterResponseNoNovelDto(cRepo.save(characterEntity));
		
		return character;
	}

	@Override
	public void deleteCharacter(int characterPk) {
//		Character characterEntity = cRepo.findById(characterPk)
//				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));
		
		cRepo.deleteById(characterPk);
	}
}
