package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.model.dto.comment.CommentResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.CommentDTO;
import com.ssafy.model.dto.EpisodeDTO;
import com.ssafy.model.dto.MemberDTO;
import com.ssafy.model.dto.member.Author;
import com.ssafy.model.entity.Comment;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.repository.CommentRepository;
import com.ssafy.model.repository.EpisodeRepository;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	CommentRepository cRepo;
	@Autowired
	EpisodeRepository eRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Page<CommentResponseDto> getComments(Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		
		Page<Comment> comments = cRepo.findAll(pageRequest);
		Page<CommentDTO> commentDTOs = 
				comments.map(comment -> {
					CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
					
					EpisodeDTO episodeDTO = modelMapper.map(comment.getEpisode(), EpisodeDTO.class);
					MemberDTO memberDTO = modelMapper.map(comment.getMember(), MemberDTO.class);
					List<Author> likedMembers= comment.getLikedMembers().stream().map(
							member -> modelMapper.map(member, Author.class)
							).collect(Collectors.toList());
					
					commentDTO.setEpisodeDTO(episodeDTO);
					commentDTO.setMemberDTO(memberDTO);
					commentDTO.setLikedMembers(likedMembers);
					return commentDTO;
				});
		return commentDTOs;
	}
	
	@Override
	public CommentResponseDto getComment(int commentPk) {
		Comment comment = cRepo.findById(commentPk).orElse(null);
		
		CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
		EpisodeDTO episodeDTO = modelMapper.map(comment.getEpisode(), EpisodeDTO.class);
		commentDTO.setEpisodeDTO(episodeDTO);
		
		return commentDTO;
	}

	@Override
	public CommentResponseDto registComment(CommentDTO commentDTO) {
		Comment comment = modelMapper.map(commentDTO, Comment.class);
		cRepo.save(comment);
	}

	@Override
	public void deleteComment(int commentPk) {
		cRepo.deleteById(commentPk);		
	}

	@Override
	public CommentResponseDto updateComment(int commentPk, CommentDTO commentDTO) {
		Comment comment = cRepo.findById(commentPk).orElse(null);
		
		comment.setCommentContent(commentDTO.getCommentContent());
		cRepo.save(comment);
	}

	@Override
	public Page<CommentResponseDto> getCommentByEpisode(int episodePk, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		Episode episode = new Episode();
		episode.setEpisodePk(episodePk);
		
		Page<Comment> comments = cRepo.findByEpisode(episode, pageRequest);
		Page<CommentDTO> commentDTOs = comments.map(comment -> {
			CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
			
			MemberDTO memberDTO = modelMapper.map(comment.getMember(), MemberDTO.class);
			
			commentDTO.setMemberDTO(memberDTO);
			return commentDTO;
		});
		
		return commentDTOs;
	}
		
	
	
}
