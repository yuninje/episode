package com.ssafy.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_pk")
    private Integer personPk;

    @Column(name = "person_birth", length = 20, nullable = false)
    private String personBirth;

    @Column(name = "person_job", length = 20, nullable =  false)
    private String personJob;

    @Column(name = "person_official", columnDefinition="TINYINT(1)", nullable = false)
    private Boolean personOfficial;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private List<Name> names = new ArrayList<>();

    // Person <-> Character
    // person 이 삭제가 되어도 소설의 등장인물은 유지 되어야하므로 remove 옵션 제거
    @OneToMany(mappedBy = "person",cascade = {CascadeType.PERSIST})
    private List<Character> characters = new ArrayList<>();

    @Builder
    public Person(String personBirth, String personJob, Boolean personOfficial, List<Name> names, List<Character> characters) {
        this.personBirth = personBirth;
        this.personJob = personJob;
        this.personOfficial = personOfficial;
        this.names = names;
        this.characters = characters;
    }

    public Person update(
            String personBirth,
            String personJob,
            Boolean personOfficial,
            List<Name> names
//            List<Character> characters
    ) {
        this.personBirth = personBirth;
        this.personJob = personJob;
        this.personOfficial = personOfficial;
        this.names = names;
//        this.characters = characters;   // 얘네는 변하지 않음.
        return this;
    }

    public void beforeDelete(){
        // name과의 관계 끊기
        names.forEach(name -> name.getPersons().remove(this));
        names = new ArrayList<>();

        // 캐릭터와의 관계 끊기 ( remove 옵션을 제거했기때문에 처리 해줘야함 )
        characters.forEach(character -> character.removePerson());
    }
}