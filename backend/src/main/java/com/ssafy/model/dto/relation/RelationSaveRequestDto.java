package com.ssafy.model.dto.relation;

import com.ssafy.model.entity.Character;
import com.ssafy.model.entity.Relation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RelationSaveRequestDto {
	private String relationrName;
	private String relationrColor;
	private String relationArrowKinds;
	private int who;
	private int whom;
	// true: 양방향, false: 단방향
	private boolean type;
	
	@Builder
	public RelationSaveRequestDto(String relationrName, String relationrColor,
			String relationArrowKinds, int who, int whom, boolean type) {
		this.relationrName = relationrName;
		this.relationrColor = relationrColor;
		this.relationArrowKinds = relationArrowKinds;
		this.who = who;
		this.whom = whom;
		this.type = type;
	}
	
	public Relation toEntity(Character who, Character whom) {
		return Relation.builder()
				.relationrName(relationrName)
				.relationrColor(relationrColor)
				.relationArrowKinds(relationArrowKinds)
				.who(who)
				.whom(whom)
				.build();
	}
}
