package com.ssafy.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.NovelDTO;

public interface NovelService {
	List<NovelDTO> getNovels();
	NovelDTO getNovel(int novelPk);
	Page<NovelDTO> getNovelsByName(String novelName, Pageable pageable);
	Page<NovelDTO> getNovlesByNick(String memNick, Pageable pageable);
	void registNovel(NovelDTO novel);
	NovelDTO updateNovel(int novelPk, NovelDTO novel);
	@Transactional
	void deleteNovel(int novelPk);
}
