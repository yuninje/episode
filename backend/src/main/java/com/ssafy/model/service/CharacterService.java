package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.character.CharacterResponseDto;
import com.ssafy.model.dto.character.CharacterResponseNoNovelDto;
import com.ssafy.model.dto.character.CharacterSaveRequestDto;
import com.ssafy.model.dto.character.CharacterUpdateRequestDto;

public interface CharacterService {
	List<CharacterResponseNoNovelDto> getCharacters();
	List<CharacterResponseNoNovelDto> getCharactersByNovel(int novelPk);
	CharacterResponseNoNovelDto getCharacter(int characterPk);
	CharacterResponseNoNovelDto registCharacter(CharacterSaveRequestDto requestDto);
	CharacterResponseNoNovelDto updateCharacter(int characterPk, CharacterUpdateRequestDto requestDto);
	void deleteCharacter(int characterPk);
}
