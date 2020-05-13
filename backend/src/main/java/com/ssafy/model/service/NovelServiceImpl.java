package com.ssafy.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dto.NovelDTO;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.NovelRepository;

@Service
public class NovelServiceImpl implements NovelService{
	@Autowired
	NovelRepository nRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Page<NovelDTO> getNovels(Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());
		
		Page<Novel> results = nRepo.findAll(pageRequest);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}

	@Override
	public NovelDTO getNovel(int novelPk) {
		Novel novel = nRepo.findById(novelPk).orElse(null);
		
		if(novel == null) return null;
		
		return modelMapper.map(novel, NovelDTO.class);
	}
	
	@Override
	public Page<NovelDTO> getNovelsByName(String novelName, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());
		
		Page<Novel> results = nRepo.findByNovelNameContaining(novelName, pageRequest);
		Page<NovelDTO> novels = results.map(Novel -> modelMapper.map(Novel, NovelDTO.class));
		
		return novels;
	}
	
	@Override
	public Page<NovelDTO> getNovlesByNick(String memNick, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("novelUpdatedAt").descending());
		
		Page<Novel> results = nRepo.findByMemNickContaining(memNick, pageRequest);
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
		Novel updateN = nRepo.findById(novelPk).orElse(null);
		
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
	
}
