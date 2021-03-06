package com.ssafy.model.dto.hashtag;

import com.ssafy.model.entity.HashTag;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HashTagResponseDto {
	private Integer hashTagPk;
    private String hashTagName;
    
    public HashTagResponseDto(HashTag hashTag) {
    	this.hashTagPk = hashTag.getHashTagPk();
		this.hashTagName = hashTag.getHashTagName();
    }
    
    @Builder
	public HashTagResponseDto(Integer hashTagPk, String hashTagName) {
		this.hashTagPk = hashTagPk;
		this.hashTagName = hashTagName;
	}
}
