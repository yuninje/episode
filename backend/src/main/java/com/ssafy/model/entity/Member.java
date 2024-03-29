package com.ssafy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mem_pk")
	private Integer memPk;
	
	@Column(name = "mem_id", length = 100, nullable = false, unique=true)
	private String memId;
	
	@Column(name = "mem_email", length = 200, nullable = false, unique=true)
	private String memEmail;
	
	@Column(name = "mem_pw", length = 512, nullable = false)
	private String memPw;
	
	@Column(name = "mem_nick", length = 100, nullable = false, unique=true)
	private String memNick;
	
	@Column(name = "mem_phone", length = 20, nullable = false, unique=true)
	private String memPhone;
	
	@Column(name = "mem_birth", length = 20, nullable = false)
	private String memBirth;
	
	@Column(name = "mem_gender", nullable = false, columnDefinition="TINYINT(1)")
	private Boolean memGender;


	// member <-> like_comment >> 댓글 좋아요
	// 이 멤버가 좋아하는 댓글들
	@ManyToMany(mappedBy = "likedMembers")
	private List<Novel> likeNovels = new ArrayList<>();
	
	// member <-> like_eipsode >> 에피소드 좋아요
	// 이 멤버가 좋아하는 에피소드들
	@ManyToMany(mappedBy = "likedMembers")
	private List<Episode> likeEpisodes = new ArrayList<>();

	// member <-> like_comment >> 댓글 좋아요
	// 이 멤버가 좋아하는 댓글들
	@ManyToMany(mappedBy = "likedMembers")
	private List<Comment> likeComments = new ArrayList<>();
	
	// member <-> novel 
	@OneToMany(mappedBy = "member",cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Novel> novels = new ArrayList<>();

	// member <-> comment 
	@OneToMany(mappedBy = "member",cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Comment> comments = new ArrayList<>();
	
	// member <-> search
	@OneToMany(mappedBy = "member",cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Search> searchs = new ArrayList<>();

	public Member(Integer memPk, String memNick) {
		super();
		this.memPk = memPk;
		this.memNick = memNick;
	}

	@Builder
	public Member(String memId, String memEmail, String memPw, String memNick, String memPhone,
			String memBirth, Boolean memGender) {
		this.memId = memId;
		this.memEmail = memEmail;
		this.memPw = memPw;
		this.memNick = memNick;
		this.memPhone = memPhone;
		this.memBirth = memBirth;
		this.memGender = memGender;
	}
	

    public Member update(String memEmail, String memPw, String memNick, String memPhone,
			String memBirth, Boolean memGender){
		this.memEmail = memEmail;
		this.memPw = memPw;
		this.memNick = memNick;
		this.memPhone = memPhone;
		this.memBirth = memBirth;
		this.memGender = memGender;
        return this;
    }

	public void beforeDelete(){
		// 소설
		for(Novel novel : this.novels){
			novel.beforeDelete();
		}
		novels = new ArrayList<>();

		// 댓글
		for(Comment comment : this.comments){
			comment.beforeDelete();
		}
		comments = new ArrayList<>();


		// 좋아요 데이터
		for(Novel novel : this.likeNovels){
			novel.getLikedMembers().remove(this);
		}
		likeNovels = new ArrayList<>();

		for(Episode episode : this.likeEpisodes){
			episode.getLikedMembers().remove(this);
		}
		likeEpisodes = new ArrayList<>();

		for(Comment comment : this.likeComments){
			comment.getLikedMembers().remove(this);
		}
		likeComments = new ArrayList<>();
	}
}
