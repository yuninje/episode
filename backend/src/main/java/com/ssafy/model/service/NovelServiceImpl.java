package com.ssafy.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
		
		return modelMapper.map(novel, NovelDTO.class);
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
	
}
