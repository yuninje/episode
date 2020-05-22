package com.ssafy.model.repository;

import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
	@Query(
			value = "SELECT * FROM EPISODE WHERE NOVEL_PK = ?1",
			nativeQuery = true)
	List<Episode> findByNovelPk(int novelPk);
	
//	@Modifying
//	@Query(
//			value = "UPDATE episode e SET "
//					+ "e.episode_title = :title, "
//					+ "e.episode_content= :content, "
//					+ "e.episode_writer = :writer, "
//					+ "e.episode_view = :view "
//					+ "WHERE e.episode_pk = :pk",
//			nativeQuery = false)
//	void updateEpisode(
//			@Param("pk") int episodePk, 
//			@Param("title") String title,
//			@Param("content") String content,
//			@Param("writer") String writer,
//			@Param("view") int view);

	
	@Modifying
	@Transactional
	@Query(
			value = "UPDATE EPISODE SET "
					+ "EPISODE_TITLE = :title, "
					+ "EPISODE_CONTENT = :content, "
					+ "EPISODE_WRITER = :writer, "
					+ "EPISODE_VIEW = :view "
					+ "WHERE EPISODE_PK = :pk",
			nativeQuery = true)
	void updateEpisode(
			@Param("pk") int episodePk,
			@Param("title") String title,
			@Param("content") String content,
			@Param("writer") String writer,
			@Param("view") int view);


	Page<Episode> findByNovel(Novel novel, Pageable pageable);
}
