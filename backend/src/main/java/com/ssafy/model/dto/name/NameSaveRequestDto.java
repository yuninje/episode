package com.ssafy.model.dto.name;

import com.ssafy.model.entity.Name;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NameSaveRequestDto {
    private String name;

    @Builder
    public NameSaveRequestDto(String name) {
        this.name = name;
    }

    public Name toEntity(){
        return Name.builder()
                .nameName(name)
                .build();
    }
}
