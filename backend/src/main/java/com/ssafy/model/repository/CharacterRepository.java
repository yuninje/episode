package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.model.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
	@Query(
			value = "select * from character_ where novel_pk = ?1",
			nativeQuery = true)
	List<Character> findByNovelPk(int novelPk);
}
