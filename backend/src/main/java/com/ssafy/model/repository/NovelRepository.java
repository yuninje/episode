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
public interface NovelRepository extends JpaRepository<Novel, Integer> {
	List<Novel> findByNovelNameContaining(String novel_name); 
	List<Novel> findByMember(Member member);
	@Query(
		value = "select new com.ssafy.model.entity.Novel(n.novelPk, n.novelName, n.novelIntro, "
			+ "n.novelImage, n.novelLimit, n.novelOpen, n.novelStatus, n.novelOnly, "
			+ "n.novelUpdatedAt, m, group_concat(g.genreName)) "
			+ "from Novel n, Member m, NovelGenre ng, Genre g "
			+ "where n.member.memPk = m.memPk "
			+ "and n.novelPk = ng.novel.novelPk "
			+ "and ng.genre.genrePk = g.genrePk ",
		countQuery = "select count(*) from Novel n "
			+ "inner join n.member m ")
	Page<Novel> findAll(Pageable pageable);
	@Query(
		value = "select new com.ssafy.model.entity.Novel(n.novelPk, n.novelName, n.novelIntro, "
			+ "n.novelImage, n.novelLimit, n.novelOpen, n.novelStatus, n.novelOnly, "
			+ "n.novelUpdatedAt, m, group_concat(g.genreName)) "
			+ "from Novel n, Member m, NovelGenre ng, Genre g "
			+ "where n.member.memPk = m.memPk "
			+ "and n.novelPk = ng.novel.novelPk "
			+ "and ng.genre.genrePk = g.genrePk "
			+ "and n.novelPk=?1")
	Optional<Novel> findById(int novelPk);
	@Query(
		value = "select new com.ssafy.model.entity.Novel(n.novelPk, n.novelName, n.novelIntro, "
			+ "n.novelImage, n.novelLimit, n.novelOpen, n.novelStatus, n.novelOnly, "
			+ "n.novelUpdatedAt, m, group_concat(g.genreName)) "
			+ "from Novel n, Member m, NovelGenre ng, Genre g "
			+ "where n.member.memPk = m.memPk "
			+ "and n.novelPk = ng.novel.novelPk "
			+ "and ng.genre.genrePk = g.genrePk "
			+ "and n.novelName like %?1% ",
		countQuery = "select count(*) from Novel n "
			+ "inner join n.member m "
			+ "where n.novelName like %?1% ")
	Page<Novel> findByNovelNameContaining(String novelName, Pageable pageable); 
	@Query(
		value = "select new com.ssafy.model.entity.Novel(n.novelPk, n.novelName, n.novelIntro, "
			+ "n.novelImage, n.novelLimit, n.novelOpen, n.novelStatus, n.novelOnly, "
			+ "n.novelUpdatedAt, m, group_concat(g.genreName)) "
			+ "from Novel n, Member m, NovelGenre ng, Genre g "
			+ "where n.member.memPk = m.memPk "
			+ "and n.novelPk = ng.novel.novelPk "
			+ "and ng.genre.genrePk = g.genrePk "
			+ "and m.memNick like %?1% ",
		countQuery = "select count(*) from Novel n "
			+ "inner join n.member m "
			+ "where memNick like %?1% ")
	Page<Novel> findByMemNickContaining(String memNick, Pageable pageable);
}
