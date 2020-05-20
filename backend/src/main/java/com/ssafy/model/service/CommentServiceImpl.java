package com.ssafy.model.service;

import com.ssafy.model.dto.comment.CommentResponseDto;
import com.ssafy.model.dto.comment.CommentSaveRequestDto;
import com.ssafy.model.dto.comment.CommentUpdateRequestDto;
import com.ssafy.model.entity.*;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
		Member member = mRepo.findById(requestDto.getMemPk())
				.orElseThrow(() -> new MemberException(MemberException.NOT_EXIST));
		Episode episode = eRepo.findById(requestDto.getEpisodePk())
				.orElseThrow(() -> new EpisodeException(EpisodeException.NOT_EXIST));

		Comment commentEntity = requestDto.toEntity(member, episode);
		commentEntity = cRepo.save(commentEntity);

		CommentResponseDto comment = new CommentResponseDto(commentEntity);
		return comment;
	}

	@Transactional
	@Override
	public void deleteComment(int commentPk) {
		Comment commentEntity = cRepo.findById(commentPk).orElseThrow(()->
				new IllegalArgumentException("comment " + commentPk + "가 존재하지 않습니다."));

		// 좋아요 데이터 삭제
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

	@Transactional
	public void deleteComment(Comment comment){
		comment.beforeDelete();
		cRepo.save(comment);
		cRepo.delete(comment);
	}

	@Transactional
	public void deleteAllComment(){
		List<Comment> commentList = cRepo.findAll();
		commentList.stream().forEach(comment -> deleteComment(comment));
	}
}
