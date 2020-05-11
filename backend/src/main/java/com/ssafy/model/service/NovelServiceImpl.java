package com.ssafy.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.Novel;
import com.ssafy.model.repository.NovelRepository;

@Service
public class NovelServiceImpl implements NovelService{
	@Autowired
	NovelRepository nRepo;
	
	@Override
	public List<Novel> getNovels() {
		List<Novel> novels = nRepo.findAll();
		return novels;
	}

	@Override
	public Novel getNovel(int novelPk) {
		Optional<Novel> novel = nRepo.findById(novelPk);
		return novel.orElse(null);
	}

	@Override
	public void registNovel(Novel novel) {
		nRepo.save(novel);
	}

	@Override
	public boolean updateNovel(Novel novel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteNovel(int novelPk) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
