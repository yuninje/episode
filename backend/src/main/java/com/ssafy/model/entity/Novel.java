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
	private LocalDateTime novelUpdatedAt = LocalDateTime.now();
	
	// novel <-> member >> N : 1 관계  
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;

	// novel <-> episode >> 1: N 관계
	@OneToMany(mappedBy = "novel", cascade = CascadeType.REMOVE)
	private List<Episode> episodes = new ArrayList<Episode>();
	
	// novel <-> genre >> N : M 관계
	@ManyToMany(mappedBy = "novels")
	private List<Genre> genres = new ArrayList<>();

	// 이 소설을 좋아하는 사람들 | novel : member = N : M
	@ManyToMany
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
	private Long likes = 0L;
	@Transient
	private Long recommends = 0L;

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

	public void belongGenre(Genre genre){
		genres.add(genre);
		genre.getNovels().add(this);
	}

	public void notBelongGenre(Genre genre){
		genres.remove(genre);
		genre.getNovels().remove(this);
	}
}
