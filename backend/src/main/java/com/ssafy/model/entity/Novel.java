package com.ssafy.model.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "novel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Novel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "novel_pk")
	private Integer novelPk;
	
	@Column(name = "novel_name", length = 150, nullable = false)
	private String novelName;
	
	@Column(name = "novel_intro", length = 1000, nullable = true)
	private String novelIntro;
	
	@Column(name = "novel_image", length = 300, nullable = true)
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
	@OneToMany(mappedBy = "novel", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Episode> episodes = new ArrayList<Episode>();

	// novel <-> novelSetting >> 1: N 관계
	@OneToMany(mappedBy = "novel", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<NovelSetting> novelSettings = new ArrayList<NovelSetting>();

	// novel <-> genre >> N : M 관계
	@ManyToMany(mappedBy = "novels", cascade = CascadeType.PERSIST)
	private List<Genre> genres = new ArrayList<>();

	// novel <-> hashtag >> N : M 관계
	@ManyToMany(mappedBy = "novels", cascade = CascadeType.PERSIST)
	private List<HashTag> hashTags = new ArrayList<>();
	
	// novel <-> character >> 1: N 관계
	@OneToMany(mappedBy = "novel", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Character> characters = new ArrayList<Character>();

	// 이 소설을 좋아하는 사람들 | novel : member = N : M
	@ManyToMany
	@JoinTable(
			name = "like_novel",
			joinColumns = @JoinColumn(name = "novel_pk"),
			inverseJoinColumns = @JoinColumn(name = "mem_pk")
	)
	private List<Member> likedMembers = new ArrayList<>();


	@Builder
	public Novel(Member member, List<Genre> genres, List<HashTag> hashTags, String novelName, String novelIntro, String novelImage, Boolean novelLimit, Boolean novelOpen, Integer novelStatus, Boolean novelOnly) {
		this.member = member;
		this.genres = genres;
		this.hashTags = hashTags;
		this.novelName = novelName;
		this.novelIntro = novelIntro;
		this.novelImage = novelImage;
		this.novelLimit = novelLimit;
		this.novelOpen = novelOpen;
		this.novelStatus = novelStatus;
		this.novelOnly = novelOnly;
	}

	public Novel update(List<Genre> uGenres, List<HashTag> uHashTags, String novelName, String novelIntro, String novelImage, Boolean novelLimit, Boolean novelOpen, Integer novelStatus, Boolean novelOnly){
		List<Genre> oGenres = this.genres;
		List<HashTag> oHashTags = this.hashTags;

		oGenres.forEach(genre -> {
			if(!uGenres.contains(genre)){	// 삭제
				genre.getNovels().remove(this);
			}
		});
		uGenres.forEach(genre -> {
			if(!oGenres.contains(genre)){
				genre.getNovels().add(this);
			}
		});

		oHashTags.forEach(hashTag -> {
			if(!uHashTags.contains(hashTag)){
				hashTag.getNovels().remove(this);
			}
		});
		uHashTags.forEach(hashTag -> {
			if(!oHashTags.contains(hashTag)){
				hashTag.getNovels().add(this);
			}
		});

		this.genres = uGenres;
		this.hashTags = uHashTags;
		this.novelName = novelName;
		this.novelIntro = novelIntro;
		this.novelImage = novelImage;
		this.novelLimit = novelLimit;
		this.novelOpen = novelOpen;
		this.novelStatus = novelStatus;
		this.novelOnly = novelOnly;
		return this;
	}

	public void updateUpdatedAt(){
		this.novelUpdatedAt = LocalDateTime.now();
	}
	public void updateView(){ this.novelView += 1;}
	public void updateImage(String imageUrl){ this.novelImage = imageUrl;}


	public void beforeDelete(){
		// 에피소드
		for(Episode episode : this.episodes){
			episode.beforeDelete();
		}

		// 소설설정
		for(NovelSetting novelSetting : this.novelSettings){
			novelSetting.beforeDelete();
		}

		// 장르
		for (Genre genre : this.genres){
			genre.getNovels().remove(this);
		}
		genres = new ArrayList<>();

		// 해쉬태그
		for (HashTag hashTag : this.hashTags){
			hashTag.getNovels().remove(this);
		}
		hashTags = new ArrayList<>();

		// 연재요일 | 추가 예정

		// 좋아요 데이터
		for (Member member : this.likedMembers) {
			member.getLikeNovels().remove(this);
		}
		likedMembers = new ArrayList<>();
	}
}
