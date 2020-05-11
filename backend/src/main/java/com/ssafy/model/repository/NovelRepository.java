package com.ssafy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer> {
	List<Novel> findByNovelNameContaining(String novel_name); 
}
