package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.GenreDTO;

public interface GenreService {
	List<GenreDTO> getGenres();
	GenreDTO getGenre(int genrePk);
	void registGenre(GenreDTO genreDTO);
	void deleteGenre(int genrePk);
}
