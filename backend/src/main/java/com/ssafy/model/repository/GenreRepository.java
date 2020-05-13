package com.ssafy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>  {
	
}
