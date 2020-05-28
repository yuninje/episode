package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.character.CharacterResponseDto;
import com.ssafy.model.dto.character.CharacterResponseNoNovelDto;
import com.ssafy.model.dto.character.CharacterSaveRequestDto;

public interface CharacterService {
	List<CharacterResponseNoNovelDto> getCharactersByNovel(int novelPk);
	CharacterResponseNoNovelDto getCharacter(int characterPk);
	CharacterResponseNoNovelDto registCharacter(CharacterSaveRequestDto requestDto);
	void deleteCharacter(int characterPk);
}
