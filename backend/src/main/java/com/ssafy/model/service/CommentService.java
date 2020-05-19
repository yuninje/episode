package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.comment.CommentSaveRequestDto;
import com.ssafy.model.dto.comment.CommentUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
	Page<CommentResponseDto> getComments(Pageable pageable);
	CommentResponseDto getComment(int commentPk);
	CommentResponseDto registComment(CommentSaveRequestDto requestDto);
	void deleteComment(int commentPk);
	CommentResponseDto updateComment(int commentPk, CommentUpdateRequestDto requestDto);
	Page<CommentResponseDto> getCommentByEpisode(int episodePk, Pageable pageable);
}
