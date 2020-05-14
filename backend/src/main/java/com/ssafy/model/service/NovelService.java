package com.ssafy.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.NovelDTO;

public interface NovelService {
	Page<NovelDTO> getNovels(Pageable pageable);
	NovelDTO getNovel(int novelPk);
//	List<NovelDTO> getNovelsByName(String novelName);
	List<NovelDTO> getNovelsByGenre(int genrePk);
	Page<NovelDTO> getNovelsByName(String novelName, Pageable pageable);
	Page<NovelDTO> getNovlesByNick(String memNick, Pageable pageable);
	void registNovel(NovelDTO novel);
	NovelDTO updateNovel(int novelPk, NovelDTO novel);
	@Transactional
	void deleteNovel(int novelPk);
	void updateGenreOfNovel(int novelPk, List<Integer> genrePks);
	Object getNovelByMember(int memPk);
}
