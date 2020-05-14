package com.ssafy.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.model.dto.CommentDTO;

public interface CommentService {
	Page<CommentDTO> getComments(Pageable pageable);
	CommentDTO getComment(int commentPk);
	void registComment(CommentDTO commentDTO);
	void deleteComment(int commentPk);
	void updateComment(int commentPk, CommentDTO commentDTO);
	Page<CommentDTO> getCommentByEpisode(int episodePk, Pageable pageable);
}
