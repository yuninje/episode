package com.ssafy.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "novel")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Novel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "novel_pk")
	private Integer novelPk;
	
	@Column(name = "novel_name", length = 30, nullable = false)
	private String novelName;
	
	@Column(name = "novel_intro", length = 100, nullable = true)
	private String novelIntro;
	
	@Column(name = "novel_image", length = 50, nullable = true)
	private String novelImage;
	
	@Column(name = "novel_limit", columnDefinition="TINYINT(1)", nullable = false)
	private Boolean novelLimit;
	
	@Column(name = "novel_open", columnDefinition="TINYINT(1)", nullable = false)
	private Boolean novelOpen;

	@Column(name = "novel_status", columnDefinition="TINYINT(1)", nullable = false)
	private Integer novelStatus;
	
	@Column(name = "novel_only", columnDefinition="TINYINT(1)", nullable = false)
	private Boolean novelOnly;
	
	@Column(name = "novel_view")
	private Long novelView = 0L;
	
	@LastModifiedDate
	@Column(name = "novel_updated_at", nullable = false)
	private Date novelUpdatedAt;
	
	// novel <-> member >> N : 1 관계  
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;

	// novel <-> episode >> 1: N 관계
	@OneToMany(mappedBy = "novel")
	private List<Episode> episodes = new ArrayList<Episode>();
	

	// novel <-> like_novel >> 소설 즐겨찾기 ( = 소설 좋아요 )
	// 이 소설을 좋아하는 멤버들 
	@OneToMany(mappedBy = "novel")
	private List<LikeNovel> membersLikeNovel = new ArrayList<>();
	
	@Transient
	private String genreName;
	@Transient
	private List<String> genreList;
	@Transient
	private String hashTagName;
	@Transient
	private List<String> hashTagList;
	@Transient
	private Long likes;
	@Transient
	private Long recommends;
	
	public Novel(Integer novelPk, String novelName, String novelIntro, String novelImage, Boolean novelLimit,
			Boolean novelOpen, Integer novelStatus, Boolean novelOnly, Date novelUpdatedAt, Member member,
			String genreName) {
		super();
		this.novelPk = novelPk;
		this.novelName = novelName;
		this.novelIntro = novelIntro;
		this.novelImage = novelImage;
		this.novelLimit = novelLimit;
		this.novelOpen = novelOpen;
		this.novelStatus = novelStatus;
		this.novelOnly = novelOnly;
		this.novelUpdatedAt = novelUpdatedAt;
		this.member = member;
		this.genreName = genreName;
		this.genreList = Arrays.asList(genreName.split(","));
	}
	
	public Novel(Integer novelPk, String novelName, String novelIntro, String novelImage, Boolean novelLimit,
			Boolean novelOpen, Integer novelStatus, Boolean novelOnly, Date novelUpdatedAt, Member member,
			String genreName, String hashTagName, Long likes, Long recommends) {
		super();
		this.novelPk = novelPk;
		this.novelName = novelName;
		this.novelIntro = novelIntro;
		this.novelImage = novelImage;
		this.novelLimit = novelLimit;
		this.novelOpen = novelOpen;
		this.novelStatus = novelStatus;
		this.novelOnly = novelOnly;
		this.novelUpdatedAt = novelUpdatedAt;
		this.member = member;
//		this.genreList = Arrays.asList(genreName.split(","));
		this.genreList = new ArrayList<String>(new HashSet<String>(Arrays.asList(genreName.split(","))));
		this.hashTagList = hashTagName != null 
				? new ArrayList<String>(new HashSet<String>(Arrays.asList(hashTagName.split(","))))
				: new ArrayList<>();
//		this.hashTagList = new ArrayList<String>(new HashSet<String>(this.hashTagList));
		this.likes = likes;
		this.recommends = recommends;
	}
}