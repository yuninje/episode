package com.ssafy.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "like_comment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeComment { // 댓글 좋아요
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int likeCommentPk;
	
	
	// like_comment <-> member
	@ManyToOne
	@JoinColumn(name="mem_pk")
	private Member member;
	

	// like_comment <-> comment
	@ManyToOne
	@JoinColumn(name="comment_pk")
	private Comment comment;
}