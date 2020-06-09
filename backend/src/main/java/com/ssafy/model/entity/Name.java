package com.ssafy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nameName")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_pk")
    private Integer namePk;

    @Column(name = "name_name", length = 30, unique = true, nullable = false)
    private String nameName;


    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "person_name",
            joinColumns = @JoinColumn(name = "name_pk"),
            inverseJoinColumns = @JoinColumn(name = "person_pk")
    )
    private List<Person> persons = new ArrayList<>();

    @Builder
    public Name(String nameName) {
        this.nameName = nameName;
    }

    public Name update(String nameName){
        this.nameName = nameName;
        return this;
    }

    public void beforeDelete(){
        // 사용할 일이 없을 예정.

        // 해당 이름을 가지고 있는 인물들과 관계 끊기
        this.getPersons().forEach(person -> person.getNames().remove(this));
        this.persons = new ArrayList<>();
    }
}