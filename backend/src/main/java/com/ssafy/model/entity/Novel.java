package com.ssafy.model.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

	@Column(name = "novel_likes")
	private Long novelLikes = 0L;

	@Column(name = "novel_recommends")
	private Long novelRecommends = 0L;

	@LastModifiedDate
	@Column(name = "novel_updated_at", nullable = false)
	private LocalDateTime novelUpdatedAt = LocalDateTime.now();


	// novel <-> member >> N : 1 관계  
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;

	// novel <-> episode >> 1: N 관계
	@OneToMany(mappedBy = "novel", cascade = CascadeType.REMOVE)
	private List<Episode> episodes = new ArrayList<Episode>();
	
	// novel <-> genre >> N : M 관계
	@ManyToMany(mappedBy = "novels", cascade = CascadeType.ALL)
	private List<Genre> genres = new ArrayList<>();

	// novel <-> hashtag >> N : M 관계
	@ManyToMany(mappedBy = "novels", cascade = CascadeType.ALL)
	private List<HashTag> hashTags = new ArrayList<>();

	// 이 소설을 좋아하는 사람들 | novel : member = N : M
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
			name = "like_novel",
			joinColumns = @JoinColumn(name = "novel_pk"),
			inverseJoinColumns = @JoinColumn(name = "mem_pk")
	)
	private List<Member> likedMembers = new ArrayList<>();



	@Transient
	private String genreName;
	@Transient
	private List<String> genreList;
	@Transient
	private String hashTagName;
	@Transient
	private List<String> hashTagList;

	public Novel(Integer novelPk, String novelName, String novelIntro, String novelImage, Boolean novelLimit,
			Boolean novelOpen, Integer novelStatus, Boolean novelOnly, LocalDateTime novelUpdatedAt, Member member,
			String genreName, String hashTagName, Long novelLikes, Long novelRecommends) {
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
		this.novelLikes = novelLikes;
		this.novelRecommends = novelRecommends;
	}

	public Novel(Integer novelPk, String novelName, String novelIntro, String novelImage, Boolean novelLimit,
			Boolean novelOpen, Integer novelStatus, Boolean novelOnly, LocalDateTime novelUpdatedAt,
			Member member, List<Genre> genres, String hashTagName, Long novelLikes, Long novelRecommends) {
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
		System.out.println(genres);
//		this.genres = genres;
		this.hashTagName = hashTagName;
		this.hashTagList = hashTagName != null 
				? new ArrayList<String>(new HashSet<String>(Arrays.asList(hashTagName.split(","))))
				: new ArrayList<>();
		this.novelLikes = novelLikes;
		this.novelRecommends = novelRecommends;
	}

	@Builder
	public Novel(Member member, String novelName, String novelIntro, String novelImage, Boolean novelLimit, Boolean novelOpen, Integer novelStatus, Boolean novelOnly) {
		this.member = member;
		this.novelName = novelName;
		this.novelIntro = novelIntro;
		this.novelImage = novelImage;
		this.novelLimit = novelLimit;
		this.novelOpen = novelOpen;
		this.novelStatus = novelStatus;
		this.novelOnly = novelOnly;
	}

	public Novel update(String novelName, String novelIntro, String novelImage, Boolean novelLimit, Boolean novelOpen, Integer novelStatus, Boolean novelOnly){
		this.novelName = novelName;
		this.novelIntro = novelIntro;
		this.novelImage = novelImage;
		this.novelLimit = novelLimit;
		this.novelOpen = novelOpen;
		this.novelStatus = novelStatus;
		this.novelOnly = novelOnly;
		return this;
	}
}
