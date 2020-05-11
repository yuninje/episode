package com.ssafy.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mem_pk")
	private int memPk;
	
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
	private boolean memGender;

	// member <-> like_novel >> 소설 즐겨찾기 ( = 소설 좋아요 )
	// 이 멤버가 좋아하는 소설들
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<LikeNovel> NovelsLikedMember = new ArrayList<>();
	
	// member <-> like_eipsode >> 에피소드 좋아요
	// 이 멤버가 좋아하는 에피소드들
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<LikeEpisode> EpisodesLikedMember = new ArrayList<>();

	// member <-> like_comment >> 댓글 좋아요
	// 이 멤버가 좋아하는 댓글들
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<LikeComment> CommentsLikedMember = new ArrayList<>();
	
	// member <-> novel 
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Novel> novels = new ArrayList<Novel>();
	
	// member <-> search
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Search> searchs = new ArrayList<Search>();
}
