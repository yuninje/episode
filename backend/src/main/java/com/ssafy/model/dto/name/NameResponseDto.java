package com.ssafy.model.dto.name;

import com.ssafy.model.entity.Name;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NameResponseDto {
    private Integer namePk;
    private String nameName;

    public NameResponseDto(Name nameName){
        this.namePk = nameName.getNamePk();
        this.nameName = nameName.getNameName();
    }

    @Builder
    public NameResponseDto(Integer namePk, String nameName) {
        this.namePk = namePk;
        this.nameName = nameName;
    }
}
