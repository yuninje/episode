package com.ssafy.model.service;

import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.genre.GenreSaveRequestDto;
import com.ssafy.model.dto.genre.GenreUpdateRequestDto;
import com.ssafy.model.entity.Genre;

import java.util.List;

public interface GenreService {
	List<GenreResponseDto> getGenres();
	GenreResponseDto getGenre(int genrePk);
	GenreResponseDto registGenre(GenreSaveRequestDto requestDto);
	GenreResponseDto updateGenre(int genrePk, GenreUpdateRequestDto requestDto);
	void deleteGenre(int genrePk);
	void deleteGenre(Genre genre);
	void deleteAllGenre();
}
