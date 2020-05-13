package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.GenreDTO;
import com.ssafy.model.entity.Genre;

public interface GenreService {
	List<GenreDTO> getGenres();
	GenreDTO getGenre(int genrePk);
	void registGenre(GenreDTO genreDTO);
	void deleteGenre(int genrePk);
}
