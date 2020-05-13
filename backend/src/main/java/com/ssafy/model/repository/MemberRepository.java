package com.ssafy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findByMemId(String memId);
	Member findByMemEmail(String memEmail);
	Member findByMemNick(String memNick);
	Member findByMemPhone(String memPhone);
	void deleteByMemId(String memId);
}
