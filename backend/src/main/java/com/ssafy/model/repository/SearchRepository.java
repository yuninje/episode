package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.entity.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer>, SearchCustomRepository {

}
