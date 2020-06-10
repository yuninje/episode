package com.ssafy.model.repository;

import com.querydsl.core.BooleanBuilder;
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
	public Page<Novel> findAll(Pageable pageable) {
		JPAQuery<Novel> query = 
				queryFactory.select(novel)
				.from(novel)
				.where(novel.member.memPk.eq(8).not());
		
		List<Novel> novels = getQuerydsl().applyPagination(pageable, query).fetch();
		
		return new PageImpl<>(novels, pageable, query.fetchCount()); 
	}
	
	@Override
	public List<Novel> findTop100ByOrderByNovelViewDesc() {
		JPAQuery<Novel> query = 
				queryFactory.select(novel)
				.from(novel)
				.where(novel.member.memPk.eq(8).not())
				.orderBy(novel.novelView.desc())
				.limit(100);
		
		return query.fetch();
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
					.or(hashTagLike(word))
					.and(novel.member.memPk.eq(8).not()));
			break;
		case "author_name":
			query.where(authorNameLike(word)
					.and(novel.member.memPk.eq(8).not()));
			break;
		case "novel_name":
			query.where(novelNameLike(word)
					.and(novel.member.memPk.eq(8).not()));
			break;
		case "hashtag":
			query.where(hashTagLike(word)
					.and(novel.member.memPk.eq(8).not()));
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
					.and(novel.genres.any().genrePk.eq(genrePk))
					.and(novel.member.memPk.eq(8).not()));
			break;
		case "author_name":
			query.where(authorNameLike(word)
					.and(novel.genres.any().genrePk.eq(genrePk))
					.and(novel.member.memPk.eq(8).not()));
			break;
		case "novel_name":
			query.where(novelNameLike(word)
					.and(novel.genres.any().genrePk.eq(genrePk))
					.and(novel.member.memPk.eq(8).not()));
			break;
		case "hashtag":
			query.where(hashTagLike(word)
					.and(novel.genres.any().genrePk.eq(genrePk))
					.and(novel.member.memPk.eq(8).not()));
			break;
		}
		
		List<Novel> novels = getQuerydsl().applyPagination(pageable, query).fetch();
		
		return new PageImpl<>(novels, pageable, query.fetchCount());
	}
	
	@Override
	public Page<Novel> findByFeel(int type, Pageable pageable) {
		JPAQuery<Novel> query = 
				queryFactory.select(novel)
				.from(novel);
		
		query.where(feel(type -1));
		
		List<Novel> novels = getQuerydsl().applyPagination(pageable, query).fetch();
		
		return new PageImpl<>(novels, pageable, query.fetchCount());
	}
	
	@Override
	public Page<Novel> findByFeelAndGenre(int type, Pageable pageable, int genrePk) {
		JPAQuery<Novel> query = 
				queryFactory.select(novel)
				.from(novel);
		
		query.where(feel(type -1).and(novel.genres.any().genrePk.eq(genrePk)));
		
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
	
	private BooleanBuilder feel(int type) {
		String[][] hashTags = {
			{"사이다", "통쾌", "고구마", "쎈케", "쎈", "김치", "막장", "능력자", "갑질", "복수", "뒷통수"},
			{"쎈", "쎈케", "능력자", "회장", "사장", "지존", "랭커", "왕", "황제", "여왕", "황후", "왕자", "신", 
				"절대신", "사기캐", "치트키", "마스터", "마왕", "아이돌", "천재", "군주", "먼치킨", "역대급"},
			{"정략결혼", "연인", "썸", "로멘스", "팜므파탈", "옴므파탈", "짐승남", "운명", "우연"},
			{"회귀", "빙의", "시간", "시간여행", "미래", "과거", "환생", "저승", "부활"},
			{"미스테리", "스릴러", "범인", "살인범", "범죄자", "범죄", "경찰", "도둑", "살인"},
			{"꿀잼", "하이텐션", "말빨"}
		};
		
		BooleanBuilder builder = new BooleanBuilder();
		for(int j=0; j<hashTags[type].length; j++) {
			builder.or(novel.hashTags.any().hashTagName.containsIgnoreCase(hashTags[type][j]));
		}
		
		return builder;
	}
}
