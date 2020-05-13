package com.ssafy.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.GenreDTO;
import com.ssafy.model.dto.NovelDTO;
import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.GenreRepository;
import com.ssafy.model.repository.NovelRepository;

@Service
public class GenreServiceImpl implements GenreService{

	@Autowired
	GenreRepository gRepo;
	@Autowired
	NovelRepository nRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<GenreDTO> getGenres() {
		List<Genre> genres = gRepo.findAll();
		List<GenreDTO> genreDTOs = 
				genres.stream().map(genre -> {
					GenreDTO genreDTO = modelMapper.map(genre, GenreDTO.class);
					genreDTO.setNovelDTOs(
						setNovelDTOs(genre.getNovels())
					);
					return genreDTO;
				}).collect(Collectors.toList());
		return genreDTOs;
	}

	@Override
	public GenreDTO getGenre(int genrePk) {
		Genre genre = gRepo.findById(genrePk).orElse(null);
		GenreDTO genreDTO = modelMapper.map(genre, GenreDTO.class);
		genreDTO.setNovelDTOs(setNovelDTOs(genre.getNovels()));
		return genreDTO;
	}

	@Override
	public void registGenre(GenreDTO genreDTO) {
		gRepo.save(modelMapper.map(genreDTO, Genre.class));		
	}
	
	@Override
	public void deleteGenre(int genrePk) {
		gRepo.deleteById(genrePk);
	}

	
	
	List<NovelDTO> setNovelDTOs(List<Novel> novels) {
		return novels.stream().map(novel -> {
			NovelDTO novelDTO = modelMapper.map(novel, NovelDTO.class);
			return novelDTO; 
		}).collect(Collectors.toList());
	}

}
