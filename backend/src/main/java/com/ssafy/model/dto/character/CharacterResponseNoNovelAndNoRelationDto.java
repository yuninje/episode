package com.ssafy.model.dto.character;

import com.ssafy.model.entity.Character;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CharacterResponseNoNovelAndNoRelationDto {
	private int characterPk;
	
	public CharacterResponseNoNovelAndNoRelationDto(Character character) {
		this.characterPk = character.getCharacterPk();
	}
	
	@Builder
	public CharacterResponseNoNovelAndNoRelationDto(int characterPk) {
		this.characterPk = characterPk;
	}
}
