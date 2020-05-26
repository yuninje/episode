package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NovelService {
	Page<NovelResponseDto> getNovels(Pageable pageable, String sort);
	NovelResponseDto getNovel(int novelPk);
	NovelResponseDto registNovel(NovelSaveRequestDto requestDto);
	NovelResponseDto updateNovel(int novelPk, NovelUpdateRequestDto requestDto);
	void deleteNovel(int novelPk);
	void deleteNovel(Novel novel);
	void deleteAllNovel();

	Page<NovelResponseDto> getNovelByMember(int memPk, Pageable pageable, String sort);
	List<NovelResponseDto> getTop100();
	Page<NovelResponseDto> getFeelNovels(int type, Pageable pageable, String sort, int genrePk);
	Page<NovelResponseDto> getNovelsByGenre(int genrePk, Pageable pageable, String sort);
	Page<NovelResponseDto> getNovelsBySearchWord(String type, String word, Pageable pageable, String sort, int memPk, int genrePk);
}
