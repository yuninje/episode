package com.ssafy.model.dto.person;

import com.ssafy.model.dto.name.NameResponseDto;
import com.ssafy.model.entity.Person;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonResponseDto {
    private Integer personPk;
    private String personBirth;
    private String personJob;
    private String personImage;
    private Boolean personOfficial;
    private List<NameResponseDto> names = new ArrayList<>();

    // 이 인물을 사용하는 등장인물의 리스트를 반환할 필요가 없음.
//    private List<Character> characters = new ArrayList<>();


    public PersonResponseDto(Person person) {
        this.personPk = person.getPersonPk();
        this.personBirth = person.getPersonBirth();
        this.personJob = person.getPersonJob();
        this.personImage = person.getPersonImage();
        this.personOfficial = person.getPersonOfficial();
        this.names = person.getNames().stream().map(name -> new NameResponseDto(name)).collect(Collectors.toList());
    }

    @Builder
    public PersonResponseDto(Integer personPk, String personBirth, String personJob, String personImage, Boolean personOfficial, List<NameResponseDto> names) {
        this.personPk = personPk;
        this.personBirth = personBirth;
        this.personJob = personJob;
        this.personImage = personImage;
        this.personOfficial = personOfficial;
        this.names = names;
    }
}