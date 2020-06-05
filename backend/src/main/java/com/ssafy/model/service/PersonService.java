package com.ssafy.model.service;

import com.ssafy.model.dto.person.PersonResponseDto;
import com.ssafy.model.dto.person.PersonSaveRequestDto;
import com.ssafy.model.dto.person.PersonUpdateRequestDto;
import com.ssafy.model.entity.Person;

import java.util.List;

public interface PersonService {
    List<PersonResponseDto> getPersons();
    List<PersonResponseDto> getPersonByName(String name);
    PersonResponseDto getPerson(int personPk);
    PersonResponseDto registPerson(PersonSaveRequestDto requestDto);
    PersonResponseDto updatePerson(int personPk, PersonUpdateRequestDto requestDto);
    void deletePerson(int personPk);
    void deletePerson(Person person);
}
