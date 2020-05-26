package com.ssafy.model.repository;

import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer>, NovelCustomRepository {
	Page<Novel> findByMember(Member member, Pageable pageable);
	List<Novel> findTop100ByOrderByNovelViewDesc();
}
