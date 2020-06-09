package com.ssafy.model.dto.character;

import com.ssafy.model.dto.person.PersonResponseDto;
import com.ssafy.model.dto.relation.RelationResponseDto;
import com.ssafy.model.entity.Additional;
import com.ssafy.model.entity.Character;
import com.ssafy.model.entity.Person;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CharacterResponseNoNovelDto {
	private int characterPk;
	private String characterName;
	private String characterAge;
	private boolean characterGender;
	private String characterRole;
	private String characterJob;
	private String characterPersonallity;
	private String characterSignificant;
	private Set<Additional> characterMore = new LinkedHashSet<>();
	private String characterImage;
	private Set<RelationResponseDto> relations = new LinkedHashSet<>();
	private PersonResponseDto person;

	public CharacterResponseNoNovelDto(Character character) {
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
		this.relations = character.getWhos().stream().map(
				r -> new RelationResponseDto(r)).collect(Collectors.toSet());
		if(character.getPerson() != null) this.person = new PersonResponseDto(character.getPerson());
	}
	
	@Builder
	public CharacterResponseNoNovelDto(int characterPk, String characterName, String characterAge, boolean characterGender,
									   String characterRole, String characterJob, String characterPersonallity, String characterSignificant,
									   Set<Additional> characterMore, String characterImage, Person person) {
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
		this.person = new PersonResponseDto(person);
	}
}
