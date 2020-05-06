package com.ssafy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findByMemId(String mem_id);
}
