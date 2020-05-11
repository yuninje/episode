package com.ssafy.model.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.NovelDTO;

public interface NovelService {
	List<NovelDTO> getNovels();
	NovelDTO getNovel(int novelPk);
	List<NovelDTO> getNovelsByName(String novelName);
	void registNovel(NovelDTO novel);
	NovelDTO updateNovel(NovelDTO novel);
	@Transactional
	void deleteNovel(int novelPk);
}
