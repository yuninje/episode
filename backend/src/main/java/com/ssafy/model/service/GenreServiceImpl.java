package com.ssafy.model.service;

import com.ssafy.model.dto.genre.GenreResponseDto;
import com.ssafy.model.dto.genre.GenreSaveRequestDto;
import com.ssafy.model.dto.genre.GenreUpdateRequestDto;
import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.GenreException;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.GenreRepository;
import com.ssafy.model.repository.NovelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class GenreServiceImpl implements GenreService{
	@Autowired
	GenreRepository gRepo;
	@Autowired
	NovelRepository nRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<GenreResponseDto> getGenres() {
		List<Genre> genreEntityPage = gRepo.findAll();
		List<GenreResponseDto> genres = genreEntityPage.stream().map(
				genreEntity -> new GenreResponseDto(genreEntity)).collect(Collectors.toList());

		return genres;
	}

	@Override
	public GenreResponseDto getGenre(int genrePk) {
		Genre genreEntity = gRepo.findById(genrePk).orElseThrow(()->
				new IllegalArgumentException("genre pk :  " + genrePk + "가 존재하지 않습니다."));

		GenreResponseDto genre = new GenreResponseDto(genreEntity);
		return genre;
	}

	@Override
	public GenreResponseDto registGenre(GenreSaveRequestDto requestDto) {
		Genre genreEntity = gRepo.save(requestDto.toEntity());
		GenreResponseDto genre = new GenreResponseDto(genreEntity);
		return genre;
	}

	@Override
	public GenreResponseDto updateGenre(int genrePk, GenreUpdateRequestDto requestDto) {
		Genre genre = gRepo.findById(genrePk).orElseThrow(()->
				new IllegalArgumentException("genre pk :  " + genrePk + "가 존재하지 않습니다."));

		genre.update(requestDto.getGenreName());
		genre = gRepo.save(genre);

		GenreResponseDto genreResponseDto = new GenreResponseDto(genre);
		return genreResponseDto;
	}

	@Override
	public void deleteGenre(int genrePk) {
		Genre genre = gRepo.findById(genrePk)
				.orElseThrow(() -> new GenreException(GenreException.NOT_EXIST));
		// 장르에 속한 소설 데이터 삭제
		for(Novel novel : genre.getNovels()){
			novel.notBelongGenre(genre);
		}

		gRepo.save(genre);
		gRepo.deleteById(genrePk);
	}

	public void deleteGenre(Genre genre){
		genre.beforeDelete();
		gRepo.save(genre);
		gRepo.delete(genre);
	}

	public void deleteAllGenre(){
		List<Genre> genreList = gRepo.findAll();
		genreList.forEach(genre -> deleteGenre(genre));
	}
}
