package com.ssafy.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.GenreDTO;
import com.ssafy.model.dto.NovelDTO;
import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.GenreRepository;
import com.ssafy.model.repository.NovelRepository;

@Service
public class NovelServiceImpl implements NovelService{
	@Autowired
	NovelRepository nRepo;
	@Autowired
	GenreRepository gRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<NovelDTO> getNovels() {
		List<Novel> results = nRepo.findAll();
		List<NovelDTO> novels = 
				results.stream().map(novel -> modelMapper.map(novel, NovelDTO.class))
				.collect(Collectors.toList());
		
		return novels;
	}

	@Override
	public NovelDTO getNovel(int novelPk) {
		Novel novel = nRepo.findById(novelPk).orElse(null);
		if(novel == null) return null;
		NovelDTO novelDTO = modelMapper.map(novel, NovelDTO.class);
		
		novelDTO.setGenreDTOs(
			novel.getGenres().stream().map(genre -> modelMapper.map(genre, GenreDTO.class))
				.collect(Collectors.toList())
		);		
		
		return novelDTO;
	}
	
	@Override
	public List<NovelDTO> getNovelsByName(String novelName) {
		List<Novel> results = nRepo.findByNovelNameContaining(novelName);
		List<NovelDTO> novels = 
				results.stream().map(novel -> modelMapper.map(novel, NovelDTO.class))
				.collect(Collectors.toList());
		System.out.println(results.size());
		
		return novels;
	}

	@Override
	public void registNovel(NovelDTO novel) {
		Novel registN = modelMapper.map(novel, Novel.class);
		nRepo.save(registN);
	}

	@Override
	public NovelDTO updateNovel(NovelDTO novel) {
		Novel updateN = nRepo.findById(novel.getNovelPk()).orElse(null);
		
		updateN.setNovelName(novel.getNovelName());
		updateN.setNovelIntro(novel.getNovelIntro());
		updateN.setNovelImage(novel.getNovelImage());
		updateN.setNovelLimit(novel.isNovelLimit());
		updateN.setNovelOpen(novel.isNovelOpen());
		updateN.setNovelStatus(novel.getNovelStatus());
		updateN.setNovelOnly(novel.isNovelOnly());
		updateN.setNovelUpdatedAt(new Date());
		
		return modelMapper.map(nRepo.save(updateN), NovelDTO.class);
	}

	@Override
	public void deleteNovel(int novelPk) {
		nRepo.deleteById(novelPk);
	}

	@Override
	public List<NovelDTO> getNovelsByGenre(int genrePk) {
		Genre genre = gRepo.findById(genrePk).orElse(null);
		
		List<NovelDTO> novels = 
				genre.getNovels().stream().map(novel -> modelMapper.map(novel, NovelDTO.class))
				.collect(Collectors.toList());
		
		return novels;
	}
	
	@Override
	public void updateGenreOfNovel(int novelPk, List<Integer> genrePks) {
		Novel novel = nRepo.findById(novelPk).orElse(null);
		List<Genre> oGenres = novel.getGenres(); // 원래 장르들
		// 새로운 장로들
		List<Genre> uGenres = genrePks.stream().map(genrePk -> {
			Genre genre = gRepo.findById(genrePk).orElse(null);
			if(oGenres.contains(genre)) {
				// 넘어가고
			}else {
				// 추가
				oGenres.add(genre);
				genre.getNovels().add(novel);
			}
			return genre;
		}).collect(Collectors.toList());
		
		List<Genre> removeGenres = new ArrayList<Genre>();
		for(Genre genre : oGenres) {
			if(uGenres.contains(genre)) {
				// 넘어가고 
			}else {
				// 삭제 
				genre.getNovels().remove(novel);
				removeGenres.add(genre);
			}
		}
		for(Genre genre : removeGenres) {
			oGenres.remove(genre);
		}
		nRepo.save(novel);
	}

	@Override
	public List<NovelDTO> getNovelByMember(int memPk) {
		Member member = new Member();
		member.setMemPk(memPk);
		List<Novel> novels = nRepo.findByMember(member);
		List<NovelDTO> novelDTOs = 
				novels.stream().map(novel -> modelMapper.map(novel, NovelDTO.class))
				.collect(Collectors.toList());
		
		return novelDTOs;
	}
	
	
}
