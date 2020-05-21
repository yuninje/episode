package com.ssafy.model.repository;

import com.ssafy.model.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NovelCustomRepository {
	Page<Novel> findBySearchWord(String type, String word, Pageable pageable);
	Page<Novel> findBySearchWordAndGenre(String type, String word, Pageable pageable, int genrePk);
}
