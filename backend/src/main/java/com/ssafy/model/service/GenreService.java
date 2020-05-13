package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.GenreDTO;
import com.ssafy.model.entity.Genre;
import com.ssafy.model.repository.GenreRepository;

@Service
public class GenreService implements GenreServiceImpl {
	@Autowired
	GenreRepository gRepo;
	@Autowired
	ModelMapper modelMapper;
	
	public List<GenreDTO> getGenres() {
		List<Genre> results = gRepo.findAll();
		List<GenreDTO> genres = 
				results.stream().map(genre -> modelMapper.map(genre, GenreDTO.class))
				.collect(Collectors.toList());
		
		return genres;
	}
}
