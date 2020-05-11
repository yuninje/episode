package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.Novel;

public interface NovelService {
	List<Novel> getNovels();
	Novel getNovel(int novelPk);
	void registNovel(Novel novel);
	boolean updateNovel(Novel novel);
	boolean deleteNovel(int novelPk);
}
