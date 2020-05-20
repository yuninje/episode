package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NovelService {
	Page<NovelResponseDto> getNovels(Pageable pageable, String sort);
	NovelResponseDto getNovel(int novelPk);
//	List<NovelDTO> getNovelsByName(String novelName);
	List<NovelResponseDto> getNovelsByGenre(int genrePk);
	Page<NovelResponseDto> getNovelsByName(String novelName, Pageable pageable, String sort, int memPk);
	Page<NovelResponseDto> getNovlesByNick(String memNick, Pageable pageable, String sort, int memPk);
	Page<NovelResponseDto> getNovelsByNameOrNick(String word, Pageable pageable, String sort, int memPk);
	NovelResponseDto registNovel(NovelSaveRequestDto requestDto);
	NovelResponseDto updateNovel(int novelPk, NovelUpdateRequestDto requestDto);
	@Transactional
	void deleteNovel(int novelPk);
	NovelResponseDto updateGenreOfNovel(int novelPk, List<Integer> genrePks);
	Page<NovelResponseDto> getNovelByMember(int memPk, Pageable pageable, String sort);
	void deleteNovel(Novel novel);
	void deleteAllNovel();
}
