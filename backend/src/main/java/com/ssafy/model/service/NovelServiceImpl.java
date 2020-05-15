package com.ssafy.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.GenreDTO;
import com.ssafy.model.dto.NovelDTO;
import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.Search;
import com.ssafy.model.repository.GenreRepository;
import com.ssafy.model.repository.NovelRepository;
import com.ssafy.model.repository.SearchRepository;

@Service
public class NovelServiceImpl implements NovelService{
	@Autowired
	NovelRepository nRepo;
	@Autowired
	GenreRepository gRepo;
	@Autowired
	SearchRepository sRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Page<NovelDTO> getNovels(Pageable pageable, String sort) {
		PageRequest pageRequest 
			= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
					Sort.by("novelUpdatedAt").descending());
		
		Page<Novel> results = nRepo.find("", "", pageRequest, sort);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}

	@Override
	public NovelDTO getNovel(int novelPk) {
		Novel novel = nRepo.findById(novelPk);
		NovelDTO novelDTO = modelMapper.map(novel, NovelDTO.class);
		
		return novelDTO;
	}
	
	@Override
	public Page<NovelDTO> getNovelsByName(String novelName, Pageable pageable, String sort, int memPk) {
		PageRequest pageRequest 
			= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
					Sort.by("novelUpdatedAt").descending());
		
		if(memPk != 0) {
			Member m = new Member();
			m.setMemPk(memPk);
			
			Date date = new Date();
			
			String[] words = novelName.split(" ");
			for(String word : words) {
				Search search = new Search();
				search.setMember(m);
				search.setSearchWord(word);
				search.setSearchCreatedAt(date);
				sRepo.save(search);
			}
		}
		
		Page<Novel> results = nRepo.find("novel_name", novelName, pageRequest, sort);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}
	
	@Override
	public Page<NovelDTO> getNovlesByNick(String memNick, Pageable pageable, String sort, int memPk) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());
		
		if(memPk != 0) {
			Member m = new Member();
			m.setMemPk(memPk);
			
			Date date = new Date();
			
			String[] words = memNick.split(" ");
			for(String word : words) {
				Search search = new Search();
				search.setMember(m);
				search.setSearchWord(word);
				search.setSearchCreatedAt(date);
				sRepo.save(search);
			}
		}
		
		Page<Novel> results = nRepo.find("author_name", memNick, pageRequest, sort);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}
	
	@Override
	public Page<NovelDTO> getNovelsByNameOrNick(String word, Pageable pageable, String sort, int memPk) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());
		
		if(memPk != 0) {
			Member m = new Member();
			m.setMemPk(memPk);
			
			Date date = new Date();
			
			String[] words = word.split(" ");
			for(String word_ : words) {
				Search search = new Search();
				search.setMember(m);
				search.setSearchWord(word_);
				search.setSearchCreatedAt(date);
				sRepo.save(search);
			}
		}
		
		Page<Novel> results = nRepo.find("author_or_novel", word, pageRequest, sort);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}

	@Override
	public void registNovel(NovelDTO novel) {
		Novel registN = modelMapper.map(novel, Novel.class);
		nRepo.save(registN);
	}

	@Override
	public NovelDTO updateNovel(int novelPk, NovelDTO novel) {
		Novel updateN = nRepo.findById(novelPk);
		
		if(updateN == null) return null;
		
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
		Novel novel = nRepo.findById(novelPk);
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
	public Page<NovelDTO> getNovelByMember(int memPk, Pageable pageable, String sort) {
		Page<Novel> results = nRepo.find("mem_pk", Integer.toString(memPk), pageable, sort);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}
}
