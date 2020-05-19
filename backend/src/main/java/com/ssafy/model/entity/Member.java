package com.ssafy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member")
@Getter
@ToString
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

	// member <-> like_novel >> 소설 즐겨찾기 ( = 소설 좋아요 )
	// 이 멤버가 좋아하는 소설들
//	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
//	private List<LikeNovel> NovelsLikedMember = new ArrayList<>();

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
	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Novel> novels = new ArrayList<>();

	// member <-> comment 
	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();
	
	// member <-> search
	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
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



	public void likeNovel(Novel novel){
		likeNovels.add(novel);
		novel.getLikedMembers().add(this);
	}
	public void unLikeNovel(Novel novel){
		likeNovels.remove(novel);
		novel.getLikedMembers().remove(this);
	}

	public void likeEpisode(Episode episode){
		likeEpisodes.add(episode);
		episode.getLikedMembers().remove(this);
	}
	public void unLikeEpisode(Episode episode){
		likeEpisodes.remove(episode);
		episode.getLikedMembers().remove(this);
	}

	public void likeComment(Comment comment){
		likeComments.add(comment);
		comment.getLikedMembers().add(this);
	}
	public void unLikeComment(Comment comment){
		likeComments.remove(comment);
		comment.getLikedMembers().remove(this);
	}
}
