package com.ssafy.model.repository;

import com.ssafy.model.entity.Novel;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NovelCustomRepository {
	Page<Novel> findAll(Pageable pageable);
	List<Novel> findTop100ByOrderByNovelViewDesc();
	Page<Novel> findBySearchWord(String type, String word, Pageable pageable);
	Page<Novel> findBySearchWordAndGenre(String type, String word, Pageable pageable, int genrePk);
	Page<Novel> findByFeel(int type, Pageable pageable);
	Page<Novel> findByFeelAndGenre(int type, Pageable pageable, int genrePk);
}
