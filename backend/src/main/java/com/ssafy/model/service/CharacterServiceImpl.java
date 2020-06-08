package com.ssafy.model.service;

import com.ssafy.model.dto.character.CharacterResponseNoNovelDto;
import com.ssafy.model.dto.character.CharacterSaveRequestDto;
import com.ssafy.model.dto.character.CharacterUpdateRequestDto;
import com.ssafy.model.entity.Character;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.CharacterRepository;
import com.ssafy.model.repository.NovelRepository;
import com.ssafy.model.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {
	@Autowired 
	NovelRepository nRepo;
	@Autowired
	CharacterRepository cRepo;
	@Autowired
	PersonRepository pRepo;
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

		Person person = pRepo.findById(requestDto.getPersonPk())
				.orElse(null);

		Character characterEntity = requestDto.toEntity(novel, person);
		characterEntity = cRepo.save(characterEntity);
		
		CharacterResponseNoNovelDto character = new CharacterResponseNoNovelDto(characterEntity);
		
		return character;
	}
	
	@Override
	public CharacterResponseNoNovelDto updateCharacter(int characterPk, CharacterUpdateRequestDto requestDto) {
		Character characterEntity = cRepo.findById(characterPk)
				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));

		Person personEntity = pRepo.findById(requestDto.getPersonPk())
				.orElse(null);

		characterEntity.update(
				requestDto.getCharacterName(), 
				requestDto.getCharacterAge(), 
				requestDto.isCharacterGender(), 
				requestDto.getCharacterRole(), 
				requestDto.getCharacterJob(), 
				requestDto.getCharacterPersonallity(), 
				requestDto.getCharacterSignificant(), 
				requestDto.getCharacterMore(), 
				requestDto.getCharacterImage(),
				personEntity
		);
		
		CharacterResponseNoNovelDto character = new CharacterResponseNoNovelDto(cRepo.save(characterEntity));
		
		return character;
	}

	@Override
	public void deleteCharacter(int characterPk) {
		Character characterEntity = cRepo.findById(characterPk)
				.orElseThrow(() -> new CharacterException(CharacterException.NOT_EXIST));
		deleteCharacter(characterEntity);
	}

	public void deleteCharacter(Character character){
		character.beforeDelete();
		cRepo.save(character);
		cRepo.delete(character);
	}
}
