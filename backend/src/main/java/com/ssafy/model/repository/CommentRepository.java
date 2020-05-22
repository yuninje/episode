package com.ssafy.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.model.entity.Comment;
import com.ssafy.model.entity.Episode;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	Page<Comment> findByEpisode(Episode episode, Pageable pageable);
}
