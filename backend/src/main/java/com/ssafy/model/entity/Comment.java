package com.ssafy.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@Setter
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
	private LocalDateTime commentCreatedAt;

	// comment <-> member >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;
	
	
	// comment <-> episode >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "episode_pk", nullable = false)
	private Episode episode;

	// comment <-> member >> N : M
	@ManyToMany
	@JoinTable(
			name = "like_comment",
			joinColumns = @JoinColumn(name = "comment_pk"),
			inverseJoinColumns = @JoinColumn(name = "mem_pk") 
			)	
	private List<Member> likedMembers = new ArrayList<Member>();

	@Builder
	public Comment(Integer commentPk, String commentContent, LocalDateTime commentCreatedAt, Member member, Episode episode) {
		this.commentPk = commentPk;
		this.commentContent = commentContent;
		this.commentCreatedAt = commentCreatedAt;
		this.member = member;
		this.episode = episode;
	}

	public Comment update(String commentContent){
		this.commentContent = commentContent;
		return this;
	}


	// 좋아요
	@Transactional
	public void likedMember(Member member){
		likedMembers.add(member);
//		member.getLikeNovels().remove(this);
	}
	// 좋아요 취소
	@Transactional
	public void unLikedMember(Member member){
		likedMembers.remove(member);
//		member.getLikeNovels().remove(this);
	}

	public void beforeDelete(){
		// 좋아요 데이터
		for (Member member : this.likedMembers) {
			member.unLikeComment(this);
		}
		likedMembers = new ArrayList<>();
	}
}