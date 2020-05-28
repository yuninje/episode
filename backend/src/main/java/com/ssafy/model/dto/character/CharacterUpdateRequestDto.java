package com.ssafy.model.dto.character;

import java.util.HashSet;
import java.util.Set;

import com.ssafy.model.entity.Additional;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CharacterUpdateRequestDto {
	private String characterName;
	private String characterAge;
	private boolean characterGender;
	private String characterRole;
	private String characterJob;
	private String characterPersonallity;
	private String characterSignificant;
	private Set<Additional> characterMore = new HashSet<>();
	private String characterImage;
	
	@Builder
	public CharacterUpdateRequestDto(String characterName, String characterAge, boolean characterGender,
			String characterRole, String characterJob, String characterPersonallity, String characterSignificant,
			Set<Additional> characterMore, String characterImage) {
		this.characterName = characterName;
		this.characterAge = characterAge;
		this.characterGender = characterGender;
		this.characterRole = characterRole;
		this.characterJob = characterJob;
		this.characterPersonallity = characterPersonallity;
		this.characterSignificant = characterSignificant;
		this.characterMore = characterMore;
		this.characterImage = characterImage;
	}
}
