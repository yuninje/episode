package com.ssafy.model.dto.member;

import com.ssafy.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	private int memPk;
	private String memNick;

	public Author(Member member){
		this.memPk = member.getMemPk();
		this.memNick = member.getMemNick();
	}
}
