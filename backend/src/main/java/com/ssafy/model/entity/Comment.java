package com.ssafy.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	private Integer commentPk;
	
	@Column(name = "comment_content", length = 30, nullable = false)
	private String commentContent;

	@CreatedDate
	@Column(name = "comment_created_at", nullable = false)
	private LocalDate commentCreatedAt;

	// comment <-> member >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;
	
	
	// comment <-> episode >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "episode_pk", nullable = false)
	private Episode episode;

	// comment <-> like_comment >> 댓글 좋아요
	// 이 댓글을 좋아하는 멤버들 
//	@OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
//	private List<LikeComment> MembersLikeComment = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
			name = "like_comment",
			joinColumns = @JoinColumn(name = "comment_pk"),
			inverseJoinColumns = @JoinColumn(name = "mem_pk") 
			)	
	private List<Member> likedMembers = new ArrayList<Member>();


}