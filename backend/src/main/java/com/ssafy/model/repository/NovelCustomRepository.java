package com.ssafy.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.model.entity.Novel;

public interface NovelCustomRepository {
	Page<Novel> find(String type, String word, Pageable pageable, String sort);
	Novel findByNovelPk(int novelPk);
}
