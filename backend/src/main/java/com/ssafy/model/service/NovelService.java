package com.ssafy.model.service;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NovelService {
	Page<NovelResponseDto> getNovels(Pageable pageable, String sort);
	NovelResponseDto getNovel(int novelPk);
	List<NovelResponseDto> getNovelsByGenre(int genrePk);
	Page<NovelResponseDto> getNovelsBySearchWord(String type, String word, Pageable pageable, String sort, int memPk, int genrePk);
	void registNovel(NovelSaveRequestDto requestDto);
	NovelResponseDto updateNovel(int novelPk, NovelUpdateRequestDto requestDto);
	@Transactional
	void deleteNovel(int novelPk);
	void updateGenreOfNovel(int novelPk, List<Integer> genrePks);
	Page<NovelResponseDto> getNovelByMember(int memPk, Pageable pageable, String sort);
	List<NovelResponseDto> getTop100();
}
