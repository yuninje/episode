package com.ssafy.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer>, NovelCustomRepository {
	List<Novel> findByNovelNameContaining(String novel_name); 
	List<Novel> findByMember(Member member);
}
