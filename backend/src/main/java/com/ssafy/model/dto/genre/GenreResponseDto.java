package com.ssafy.model.dto.genre;

import com.ssafy.model.entity.Genre;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GenreResponseDto {
    private Integer genrePk;
    private String genreName;

    public GenreResponseDto(Genre genre) {
        this.genrePk = genre.getGenrePk();
        this.genreName = genre.getGenreName();
    }

    @Builder
    public GenreResponseDto(Integer genrePk, String genreName) {
        this.genrePk = genrePk;
        this.genreName = genreName;
    }
}