package com.ssafy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer> {
}
