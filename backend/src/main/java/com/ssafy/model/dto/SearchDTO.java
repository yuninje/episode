package com.ssafy.model.dto;

import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
	private String searchWord;
	private Long searchCnt;
}
