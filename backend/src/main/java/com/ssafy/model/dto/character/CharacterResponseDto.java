package com.ssafy.model.dto.character;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.person.PersonResponseDto;
import com.ssafy.model.entity.Additional;
import com.ssafy.model.entity.Character;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CharacterResponseDto {
	private int characterPk;
	private String characterName;
	private String characterAge;
	private boolean characterGender;
	private String characterRole;
	private String characterJob;
	private String characterPersonallity;
	private String characterSignificant;
	private Set<Additional> characterMore = new HashSet<>();
	private String characterImage;
	private NovelResponseDto novel;
	private PersonResponseDto person;
	
	public CharacterResponseDto(Character character) {
		this.characterPk = character.getCharacterPk();
		this.characterName = character.getCharacterName();
		this.characterAge = character.getCharacterAge();
		this.characterGender = character.getCharacterGender();
		this.characterRole = character.getCharacterRole();
		this.characterJob = character.getCharacterJob();
		this.characterPersonallity = character.getCharacterPersonallity();
		this.characterSignificant = character.getCharacterSignificant();
		this.characterMore = character.getCharacterMore();
		this.characterImage = character.getCharacterImage();
		this.novel = new NovelResponseDto(character.getNovel());
		this.person = new PersonResponseDto(character.getPerson());
	}
	
	@Builder
	public CharacterResponseDto(int characterPk, String characterName, String characterAge, boolean characterGender,
								String characterRole, String characterJob, String characterPersonallity, String characterSignificant,
								Set<Additional> characterMore, String characterImage, NovelResponseDto novel, PersonResponseDto person) {
		this.characterPk = characterPk;
		this.characterName = characterName;
		this.characterAge = characterAge;
		this.characterGender = characterGender;
		this.characterRole = characterRole;
		this.characterJob = characterJob;
		this.characterPersonallity = characterPersonallity;
		this.characterSignificant = characterSignificant;
		this.characterMore = characterMore;
		this.characterImage = characterImage;
		this.novel = novel;
		this.person = person;
	}
}
