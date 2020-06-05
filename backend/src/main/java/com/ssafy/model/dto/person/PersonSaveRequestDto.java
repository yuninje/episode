package com.ssafy.model.dto.person;

import com.ssafy.model.entity.Name;
import com.ssafy.model.entity.Person;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonSaveRequestDto {
    private String personBirth;
    private String personJob;
    private Boolean personOfficial;
    private List<String> names = new ArrayList<>();

    @Builder
    public PersonSaveRequestDto(String personBirth, String personJob, Boolean personOfficial, List<String> names) {
        this.personBirth = personBirth;
        this.personJob = personJob;
        this.personOfficial = personOfficial;
        this.names = names;
    }

    public Person toEntity(List<Name> names){
        return Person.builder()
                .personBirth(personBirth)
                .personJob(personJob)
                .personOfficial(personOfficial)
                .names(names)
                .build();
    }
}