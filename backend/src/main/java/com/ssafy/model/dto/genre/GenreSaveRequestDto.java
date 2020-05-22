package com.ssafy.model.dto.genre;

import com.ssafy.model.entity.Genre;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GenreSaveRequestDto {
    private String genreName;

    @Builder
    public GenreSaveRequestDto(String genreName) {
        this.genreName = genreName;
    }

    public Genre toEntity(){
        return Genre.builder()
                .genreName(genreName)
                .build();
    }
}