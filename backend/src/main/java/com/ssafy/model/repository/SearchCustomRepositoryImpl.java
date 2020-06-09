package com.ssafy.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.model.entity.QSearch;
import com.ssafy.model.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchCustomRepositoryImpl extends QuerydslRepositorySupport implements SearchCustomRepository {
	@Autowired
	private JPAQueryFactory queryFactory;
	private QSearch search = QSearch.search;

	public SearchCustomRepositoryImpl() {
		super(Search.class);
	}

	@Override
	public List<Search> getRealTimeSearch() {
		NumberPath<Long> searchCnt = Expressions.numberPath(Long.class, "searchCnt");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yesterday = new Date(cal.getTimeInMillis());

		JPAQuery<Search> query =
				queryFactory.select(
						Projections.constructor(Search.class,
								search.searchWord.as("searchWord"),
								search.searchWord.count().as(searchCnt)
						)
				)
						.from(search)
						.where(search.searchCreatedAt.gt(yesterday))
						.groupBy(search.searchWord)
						.orderBy(searchCnt.desc());
		
		List<Search> searchs = query.fetch();

		return searchs;
	}

}
