package com.ssafy.model.dto.genre;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenreUpdateRequestDto {
    private String genreName;


    @Builder
    public GenreUpdateRequestDto(String genreName) {
        this.genreName = genreName;
    }
}
