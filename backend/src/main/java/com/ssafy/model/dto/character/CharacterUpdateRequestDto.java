package com.ssafy.model.dto.character;

import com.ssafy.model.entity.Additional;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CharacterUpdateRequestDto {
	private int personPk;
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
	public CharacterUpdateRequestDto(int personPk, String characterName, String characterAge, boolean characterGender,
			String characterRole, String characterJob, String characterPersonallity, String characterSignificant,
			Set<Additional> characterMore, String characterImage) {
		this.personPk = personPk;
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
