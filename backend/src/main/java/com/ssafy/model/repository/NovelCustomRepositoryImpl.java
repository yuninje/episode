package com.ssafy.model.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.QEpisode;
import com.ssafy.model.entity.QGenre;
import com.ssafy.model.entity.QLikeEpisode;
import com.ssafy.model.entity.QLikeNovel;
import com.ssafy.model.entity.QMember;
import com.ssafy.model.entity.QNovel;
import com.ssafy.model.entity.QNovelGenre;

public class NovelCustomRepositoryImpl extends QuerydslRepositorySupport implements NovelCustomRepository {
	@Autowired
	private JPAQueryFactory queryFactory;
	private QNovel novel = QNovel.novel;
	private QMember member = QMember.member;
	private QNovelGenre novelGenre = QNovelGenre.novelGenre;
	private QGenre genre = QGenre.genre;
	private QLikeNovel likeNovel = QLikeNovel.likeNovel;
	private QLikeEpisode likeEpisode = QLikeEpisode.likeEpisode;
	private QEpisode episode = QEpisode.episode;
	
	public NovelCustomRepositoryImpl() {
		super(Novel.class);
	}
	
	@Override
	public Page<Novel> find(String type, String word, Pageable pageable, String sort) {
		SimpleTemplate<String> simpleTemplate = Expressions.simpleTemplate(String.class, "group_concat({0})", genre.genreName);
		NumberPath<Long> likes = Expressions.numberPath(Long.class, "likes"); 
		NumberPath<Long> recommends = Expressions.numberPath(Long.class, "recommends");
		
		JPAQuery<Novel> query = 
			queryFactory.select(
				Projections.constructor(Novel.class, 
					novel.novelPk.as("novelPk"),
					novel.novelName.as("novelName"),
					novel.novelIntro.as("novelIntro"),
					novel.novelImage.as("novelImage"),
					novel.novelLimit.as("novelLimit"),
					novel.novelOpen.as("novelOpen"),
					novel.novelStatus.as("novelStatus"),
					novel.novelOnly.as("novelOnly"),
					novel.novelUpdatedAt.as("novelUpdatedAt"),
					novel.member.as("member"), 
					simpleTemplate.as("genreName"),
					ExpressionUtils.as(
						JPAExpressions.select(likeNovel.likeNovelPk.count())
							.from(likeNovel)
							.where(likeNovel.novel.novelPk.eq(novel.novelPk)),
							likes
					),
					ExpressionUtils.as(
						JPAExpressions.select(likeEpisode.likeEpisodePk.count())
							.from(likeEpisode)
							.where(likeEpisode.episode.episodePk.in(
								JPAExpressions.select(episode.episodePk)
									.from(episode)
									.where(episode.novel.novelPk.eq(novel.novelPk))
								)),
							recommends
					)
				)
			)
			.from(novel)
			.join(novel.member, member)
			.join(novelGenre).on(novelGenre.novel.novelPk.eq(novel.novelPk))
			.join(genre).on(novelGenre.genre.genrePk.eq(genre.genrePk))
			.groupBy(novel.novelPk);
		
		switch(sort) {
		case "likes":
			query.orderBy(likes.desc());
			break;
		case "recommends":
			query.orderBy(recommends.desc());
			break;
		}
		
		switch(type) {
		case "mem_pk":
			query.where(novel.member.memPk.eq(Integer.parseInt(word)));
			break;
		case "author_or_novel":
			query.where(novel.member.memNick.containsIgnoreCase(word)
					.or(novel.novelName.containsIgnoreCase(word)));
		case "author_name":
			query.where(novel.member.memNick.containsIgnoreCase(word));
			break;
		case "novel_name":
			query.where(novel.novelName.containsIgnoreCase(word));
			break;
		}
		
		List<Novel> novels = getQuerydsl().applyPagination(pageable, query).fetch();
		
		return new PageImpl<>(novels, pageable, query.fetchCount());
	}
	
	@Override
	public Novel findById(int novelPk) {
		SimpleTemplate<String> simpleTemplate = Expressions.simpleTemplate(String.class, "group_concat({0})", genre.genreName);
		JPAQuery<Novel> query = 
			queryFactory.select(
				Projections.constructor(Novel.class, 
					novel.novelPk.as("novelPk"),
					novel.novelName.as("novelName"),
					novel.novelIntro.as("novelIntro"),
					novel.novelImage.as("novelImage"),
					novel.novelLimit.as("novelLimit"),
					novel.novelOpen.as("novelOpen"),
					novel.novelStatus.as("novelStatus"),
					novel.novelOnly.as("novelOnly"),
					novel.novelUpdatedAt.as("novelUpdatedAt"),
					novel.member.as("member"), 
					simpleTemplate.as("genreName"),
					ExpressionUtils.as(
						JPAExpressions.select(likeNovel.likeNovelPk.count())
							.from(likeNovel)
							.where(likeNovel.novel.novelPk.eq(novel.novelPk)),
							"likes"
					)
				)
			)
			.from(novel)
			.where(novel.novelPk.eq(novelPk))
			.join(novel.member, member)
			.join(novelGenre).on(novelGenre.novel.novelPk.eq(novel.novelPk))
			.join(genre).on(novelGenre.genre.genrePk.eq(genre.genrePk))
			.groupBy(novel.novelPk);
		
		return query.fetchOne();
	}
}
