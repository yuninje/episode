package com.ssafy.model.dto.relation;

import java.util.Set;

import com.ssafy.model.dto.character.CharacterResponseNoNovelDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.Additional;
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
public class RelationResponseDto {
	private int relationPk;
	private String relationrName;
	private String relationrColor;
	private String relationArrowKinds;
	private CharacterResponseNoNovelDto who;
	private CharacterResponseNoNovelDto whom;
	
	public RelationResponseDto(Relation relation) {
		this.relationPk = relation.getRelationPk();
		this.relationrName = relation.getRelationrName();
		this.relationrColor = relation.getRelationrColor();
		this.relationArrowKinds = relation.getRelationArrowKinds();
		this.who = new CharacterResponseNoNovelDto(relation.getWho());
		this.whom = new CharacterResponseNoNovelDto(relation.getWhom());
	}
	
	@Builder
	public RelationResponseDto(int relationPk, String relationrName, String relationrColor, String relationArrowKinds,
			CharacterResponseNoNovelDto who, CharacterResponseNoNovelDto whom) {
		this.relationPk = relationPk;
		this.relationrName = relationrName;
		this.relationrColor = relationrColor;
		this.relationArrowKinds = relationArrowKinds;
		this.who = who;
		this.whom = whom;
	}
}
