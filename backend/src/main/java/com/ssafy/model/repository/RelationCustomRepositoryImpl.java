package com.ssafy.model.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.model.entity.QCharacter;
import com.ssafy.model.entity.QNovel;
import com.ssafy.model.entity.QRelation;
import com.ssafy.model.entity.Relation;

public class RelationCustomRepositoryImpl extends QuerydslRepositorySupport implements RelationCustomRepository {
	@Autowired
	private JPAQueryFactory queryFactory;
	private QRelation relation = QRelation.relation;
	private QCharacter character = QCharacter.character;
	private QNovel novel = QNovel.novel;
	
	public RelationCustomRepositoryImpl() {
		super(Relation.class);
	}
	
	@Override
	public List<Relation> findByNovel(int novelPk) {
		JPAQuery<Relation> query =
				queryFactory.select(relation)
						.from(relation)
						.innerJoin(character)
						.on(character.characterPk.eq(relation.who.characterPk))
						.where((JPAExpressions.select(character)
									.from(character)
									.join(novel)
									.on(novel.characters.any().characterPk.eq(character.characterPk))
									.join(relation)
									.on(relation.who.characterPk.eq(character.characterPk))
									.where()
								).exists()
								.and(character.novel.novelPk.eq(novelPk)));
		
		List<Relation> relations = query.fetch();
		
		return relations;
	}

	@Override
	public void deleteByWhoAndWhom(int who, int whom) {
		queryFactory.delete(relation)
			.where(relation.who.characterPk.eq(who)
					.and(relation.whom.characterPk.eq(whom)))
			.execute();
	}
}
