package com.ssafy.model.repository;


import com.ssafy.model.entity.Name;
import com.ssafy.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNamesLike(Name name);
}