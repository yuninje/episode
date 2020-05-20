package com.ssafy.model.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NovelCustomRepositoryImpl extends QuerydslRepositorySupport implements NovelCustomRepository {
	@Autowired
	private JPAQueryFactory queryFactory;
	private QNovel novel = QNovel.novel;
	
	public NovelCustomRepositoryImpl() {
		super(Novel.class);
	}
	
	@Override
	public Page<Novel> findBySearchWord(String type, String word, Pageable pageable) {
		JPAQuery<Novel> query = 
			queryFactory.select(novel)
			.from(novel);
		
		switch(type) {
		case "all":
			query.where(authorNameLike(word)
					.or(novelNameLike(word))
					.or(hashTagLike(word)));
			break;
		case "author_name":
			query.where(authorNameLike(word));
			break;
		case "novel_name":
			query.where(novelNameLike(word));
			break;
		case "hashtag":
			query.where(hashTagLike(word));
			break;
		}
		
		List<Novel> novels = getQuerydsl().applyPagination(pageable, query).fetch();
		
		return new PageImpl<>(novels, pageable, query.fetchCount());
	}

	@Override
	public Page<Novel> findBySearchWordAndGenre(String type, String word, Pageable pageable, int genrePk) {
		JPAQuery<Novel> query = 
			queryFactory.select(novel)
			.from(novel);
			
		switch(type) {
		case "all":
			query.where((authorNameLike(word)
					.or(novelNameLike(word))
					.or(hashTagLike(word)))
					.and(novel.genres.any().genrePk.eq(genrePk)));
			break;
		case "author_name":
			query.where(authorNameLike(word)
					.and(novel.genres.any().genrePk.eq(genrePk)));
			break;
		case "novel_name":
			query.where(novelNameLike(word)
					.and(novel.genres.any().genrePk.eq(genrePk)));
			break;
		case "hashtag":
			query.where(hashTagLike(word)
					.and(novel.genres.any().genrePk.eq(genrePk)));
			break;
		}
		
		List<Novel> novels = getQuerydsl().applyPagination(pageable, query).fetch();
		
		return new PageImpl<>(novels, pageable, query.fetchCount());
	}
	
	private BooleanBuilder authorNameLike(String word) {
		String[] wordArr = word.split(" ");
		BooleanBuilder builder = new BooleanBuilder();
		
		for(int i=0; i<wordArr.length; i++) {
			builder.or(novel.member.memNick.containsIgnoreCase(wordArr[i]));
		}
		
		return builder;
	}
	
	private BooleanBuilder novelNameLike(String word) {
		String[] wordArr = word.split(" ");
		BooleanBuilder builder = new BooleanBuilder();
		
		for(int i=0; i<wordArr.length; i++) {
			builder.or(novel.novelName.containsIgnoreCase(wordArr[i]));
		}
		
		return builder;
	}
	
	private BooleanBuilder hashTagLike(String word) {
		String[] wordArr = word.split(" ");
		BooleanBuilder builder = new BooleanBuilder();
		
		for(int i=0; i<wordArr.length; i++) {
			builder.or(novel.hashTags.any().hashTagName.containsIgnoreCase(wordArr[i]));
		}
		
		return builder;
	}
}
