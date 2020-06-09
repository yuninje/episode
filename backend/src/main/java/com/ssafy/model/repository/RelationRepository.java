package com.ssafy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.model.entity.Relation;

public interface RelationRepository extends JpaRepository<Relation, Integer>, RelationCustomRepository {

}
