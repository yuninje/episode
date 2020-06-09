package com.ssafy.model.dto.relation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RelationUpdateRequestDto {
	private String relationrName;
	private String relationrColor;
	private String relationArrowKinds;
	
	@Builder
	public RelationUpdateRequestDto(String relationrName, String relationrColor, String relationArrowKinds) {
		this.relationrName = relationrName;
		this.relationrColor = relationrColor;
		this.relationArrowKinds = relationArrowKinds;
	}
}
