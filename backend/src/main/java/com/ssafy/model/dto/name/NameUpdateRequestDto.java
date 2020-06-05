package com.ssafy.model.dto.name;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameUpdateRequestDto {
    private String name;

    @Builder
    public NameUpdateRequestDto(String name){ this.name = name;}
}
