package com.ssafy.model.repository;

import com.ssafy.model.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer>, NovelCustomRepository {
//	List<Novel> findByNovelNameContaining(String novel_name);
//	List<Novel> findByMember(Member member);
}
