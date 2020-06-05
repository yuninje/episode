package com.ssafy.model.repository;


import com.ssafy.model.entity.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NameRepository extends JpaRepository<Name, Integer> {
    Optional<Name> findByNameName(String name);
}
