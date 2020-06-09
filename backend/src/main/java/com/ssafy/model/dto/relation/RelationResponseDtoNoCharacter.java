package com.ssafy.model.dto.relation;

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
public class RelationResponseDtoNoCharacter {
	private int relationPk;
	private String relationrName;
	private String relationrColor;
	private String relationArrowKinds;
	
	public RelationResponseDtoNoCharacter(Relation relation) {
		this.relationPk = relation.getRelationPk();
		this.relationrName = relation.getRelationrName();
		this.relationrColor = relation.getRelationrColor();
		this.relationArrowKinds = relation.getRelationArrowKinds();
	}
	
	@Builder
	public RelationResponseDtoNoCharacter(int relationPk, String relationrName, String relationrColor, String relationArrowKinds) {
		this.relationPk = relationPk;
		this.relationrName = relationrName;
		this.relationrColor = relationrColor;
		this.relationArrowKinds = relationArrowKinds;
	}
}
