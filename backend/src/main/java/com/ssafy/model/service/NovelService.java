package com.ssafy.model.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.NovelDTO;

public interface NovelService {
	List<NovelDTO> getNovels();
	NovelDTO getNovel(int novelPk);
	void registNovel(NovelDTO novel);
	NovelDTO updateNovel(NovelDTO novel);
	@Transactional
	void deleteNovel(int novelPk);
}
