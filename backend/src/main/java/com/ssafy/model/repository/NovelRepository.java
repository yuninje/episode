package com.ssafy.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.model.entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer> {
	@Query(
			value = "select * from novel n "
					+ "join member "
					+ "using(mem_pk) "
					+ "where novel_name like %?1% ",
			countQuery = "select count(*) from novel n "
					+ "join member "
					+ "using(mem_pk) "
					+ "where novel_name like %?1% ",
			nativeQuery = true)
	Page<Novel> findByNovelNameContaining(String novelName, Pageable pageable); 
	@Query(
		value = "select * from novel n "
				+ "join member "
				+ "using(mem_pk) "
				+ "where mem_nick like %?1% ",
		countQuery = "select count(*) from novel n "
				+ "join member "
				+ "using(mem_pk) "
				+ "where mem_nick like %?1% ",
		nativeQuery = true)
	Page<Novel> findByMemNickContaining(String memNick, Pageable pageable);
}
