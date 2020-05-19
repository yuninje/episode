package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.comment.CommentSaveRequestDto;
import com.ssafy.model.dto.comment.CommentUpdateRequestDto;
import com.ssafy.model.entity.Comment;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Member;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	MemberRepository mRepo;
	@Autowired
	EpisodeRepository eRepo;
	@Autowired
	CommentRepository cRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Page<CommentResponseDto> getComments(Pageable pageable) {
		Page<Comment> commentEntityPage = cRepo.findAll(pageable);
		Page<CommentResponseDto> comments = commentEntityPage.map(commentEntity -> new CommentResponseDto(commentEntity));

		return comments;
	}

	@Override
	public CommentResponseDto getComment(int commentPk) {
		Comment commentEntity = cRepo.findById(commentPk).orElseThrow(()->
				new IllegalArgumentException("comment " + commentPk + "가 존재하지 않습니다."));

		CommentResponseDto comment = new CommentResponseDto(commentEntity);
		return comment;
	}

	@Override
	public CommentResponseDto registComment(CommentSaveRequestDto requestDto) {
		Comment commentEntity = requestDto.toEntity(mRepo, eRepo);
		commentEntity = cRepo.save(commentEntity);

		CommentResponseDto comment = new CommentResponseDto(commentEntity);
		return comment;
	}

	@Override
	public void deleteComment(int commentPk) {
		Comment commentEntity = cRepo.findById(commentPk).orElseThrow(()->
				new IllegalArgumentException("comment " + commentPk + "가 존재하지 않습니다."));
		for(Member memberEntity : commentEntity.getLikedMembers()){
			memberEntity.unLikeComment(commentEntity);
		}
		cRepo.save(commentEntity);
		cRepo.deleteById(commentPk);
	}

	@Override
	public CommentResponseDto updateComment(int commentPk, CommentUpdateRequestDto requestDto) {
		Comment commentEntity = cRepo.findById(commentPk).orElseThrow(()->
				new IllegalArgumentException("comment " + commentPk + "가 존재하지 않습니다."));
		commentEntity.update(requestDto.getCommentContent());

		CommentResponseDto comment = new CommentResponseDto(cRepo.save(commentEntity));
		return comment;
	}

	@Override
	public Page<CommentResponseDto> getCommentByEpisode(int episodePk, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		Episode episodeEntity = eRepo.findById(episodePk).orElseThrow(()->
				new IllegalArgumentException("episode " + episodePk + "가 존재하지 않습니다."));

		Page<Comment> commentEntityPage = cRepo.findByEpisode(episodeEntity, pageRequest);
		Page<CommentResponseDto> comments = commentEntityPage.map(commentEntity -> new CommentResponseDto(commentEntity));

		return comments;
	}
		
	
	
}
