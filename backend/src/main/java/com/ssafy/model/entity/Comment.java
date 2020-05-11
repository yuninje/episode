package com.ssafy.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_pk")
	private int commentPk;
	
	@Column(name = "comment_content", length = 30, nullable = false)
	private String commentContent;

	@CreatedDate
	@Column(name = "comment_created_at", nullable = false)
	private Date commentCreatedAt;
	
	// comment <-> episode >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "episode_pk", nullable = false)
	private Episode episode;

	// comment <-> like_comment >> 댓글 좋아요
	// 이 댓글을 좋아하는 멤버들 
	@OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
	private List<LikeComment> MembersLikeComment = new ArrayList<>();
}