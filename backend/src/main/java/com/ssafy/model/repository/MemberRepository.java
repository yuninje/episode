package com.ssafy.model.repository;

import com.ssafy.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByMemId(String memId);
	Member findByMemEmail(String memEmail);
	Member findByMemNick(String memNick);
	Member findByMemPhone(String memPhone);
	void deleteByMemId(String memId);
}
