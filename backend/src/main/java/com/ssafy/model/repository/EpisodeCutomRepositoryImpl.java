package com.ssafy.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.QEpisode;

public class EpisodeCutomRepositoryImpl extends QuerydslRepositorySupport implements EpisodeCustomRepository {
	@Autowired
	private JPAQueryFactory queryFactory;
	private QEpisode episode = QEpisode.episode;
	
	public EpisodeCutomRepositoryImpl() {
		super(Episode.class);
	}
	
	@Override
	public Episode[] findEpisodesByEpisodePk(int episodePk) {
		Episode[] episodes = new Episode[2];
		System.out.println("aaaaa");
		
		episodes[0] = queryFactory.select(episode)
					.from(episode)
					.where(episode.episodePk.max().gt(episodePk)).fetchOne();
		episodes[1] = queryFactory.select(episode)
					.from(episode)
					.where(episode.episodePk.min().lt(episodePk)).fetchOne();
		
		System.out.println(episodes.length);
		
		return episodes;
	}
}
