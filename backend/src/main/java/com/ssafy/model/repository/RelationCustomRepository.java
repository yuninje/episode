package com.ssafy.model.repository;

import java.util.List;

import com.ssafy.model.entity.Relation;

public interface RelationCustomRepository {
	List<Relation> findByNovel(int novelPk);
	void deleteByWhoAndWhom(int who, int whom);
}
