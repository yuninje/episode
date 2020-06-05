package com.ssafy.model.service;

import com.ssafy.model.dto.person.PersonResponseDto;
import com.ssafy.model.dto.person.PersonSaveRequestDto;
import com.ssafy.model.dto.person.PersonUpdateRequestDto;
import com.ssafy.model.entity.Name;
import com.ssafy.model.entity.NameException;
import com.ssafy.model.entity.NovelException;
import com.ssafy.model.entity.Person;
import com.ssafy.model.repository.NameRepository;
import com.ssafy.model.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    NameRepository nameRepository;

    @Override
    public List<PersonResponseDto> getPersons() {
        List<Person> persons = personRepository.findAll();
        List<PersonResponseDto> responseDtoList = persons.stream().map(person -> new PersonResponseDto(person)).collect(Collectors.toList());
        return responseDtoList;
    }

    @Override
    public PersonResponseDto getPerson(int personPk) {
        Person person = personRepository.findById(personPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        PersonResponseDto personResponseDto = new PersonResponseDto(person);
        return personResponseDto;
    }

    @Override
    public List<PersonResponseDto> getPersonByName(String nameStr) {
        Name name = nameRepository.findByNameName(nameStr)
                .orElseThrow(() -> new NameException(NameException.NOT_EXIST));
        List<Person> personList = personRepository.findByNamesLike(name);
        List<PersonResponseDto> responseDtos = personList.stream().map(person -> new PersonResponseDto(person)).collect(Collectors.toList());
        return responseDtos;
    }

    @Autowired
    NameService nameService;

    @Override
    public PersonResponseDto registPerson(PersonSaveRequestDto requestDto) {
        List<Name> names = requestDto.getNames().stream().map(nameStr ->
                nameService.findOrRegistName(nameStr)
        ).collect(Collectors.toList());


        Person person = personRepository.save(requestDto.toEntity(names));
        names.forEach(name -> name.getPersons().add(person));

        PersonResponseDto personResponseDto = new PersonResponseDto(person);
        return personResponseDto;
    }

    @Override
    public PersonResponseDto updatePerson(int personPk, PersonUpdateRequestDto requestDto) {    // 사용할 일이 있을까 싶음
        Person person = personRepository.findById(personPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        List<Name> names = requestDto.getNames().stream().map(s ->
            nameRepository.findByNameName(s)
                    .orElse(nameRepository.save(Name.builder().nameName(s).build()))
        ).collect(Collectors.toList());

        person.update(
                requestDto.getPersonBirth(),
                requestDto.getPersonJob(),
                requestDto.getPersonOfficial(),
                names
        );
        personRepository.save(person);

        PersonResponseDto personResponseDto = new PersonResponseDto(person);
        return personResponseDto;
    }

    @Override
    public void deletePerson(int personPk) {
        Person person = personRepository.findById(personPk)
                .orElseThrow(() -> new NovelException(NovelException.NOT_EXIST));
        deletePerson(person);
    }

    @Override
    public void deletePerson(Person person) {
        person.beforeDelete();
        personRepository.save(person);
        personRepository.delete(person);
    }
}
