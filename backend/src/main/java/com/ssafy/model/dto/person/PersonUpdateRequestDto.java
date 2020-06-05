package com.ssafy.model.dto.person;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonUpdateRequestDto {
    private String personBirth;
    private String personJob;
    private Boolean personOfficial;
    private List<String> names = new ArrayList<>();


    @Builder
    public PersonUpdateRequestDto(String personBirth, String personJob, Boolean personOfficial, List<String> names) {
        this.personBirth = personBirth;
        this.personJob = personJob;
        this.personOfficial = personOfficial;
        this.names = names;
    }
}
