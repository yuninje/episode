package com.ssafy.model.dto.hashtag;

import com.ssafy.model.entity.HashTag;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HashTagSaveRequestDto {
    String hashTagName;
    @Builder
    public HashTagSaveRequestDto(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public HashTag toEntity(){
        return HashTag.builder()
                .hashTagName(hashTagName)
                .build();
    }
}
