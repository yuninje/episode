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
	private int novelPk;
	
	@Column(name = "novel_name", length = 30, nullable = false)
	private String novelName;
	
	@Column(name = "novel_intro", length = 100, nullable = true)
	private String novelIntro;
	
	@Column(name = "novel_image", length = 50, nullable = true)
	private String novelImage;
	
	@Column(name = "novel_limit", columnDefinition="TINYINT(1)", nullable = false)
	private boolean novelLimit;
	
	@Column(name = "novel_open", columnDefinition="TINYINT(1)", nullable = false)
	private boolean novelOpen;

	@Column(name = "novel_status", columnDefinition="TINYINT(1)", nullable = false)
	private int novelStatus;
	
	@Column(name = "novel_only", columnDefinition="TINYINT(1)", nullable = false)
	private boolean novelOnly;
	
	@LastModifiedDate
	@Column(name = "novel_updated_at", nullable = false)
	private Date novelUpdatedAt;
	
	// novel <-> member >> N : 1 관계  
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;

	// novel <-> like_novel >> 소설 즐겨찾기 ( = 소설 좋아요 )
	// 이 소설을 좋아하는 멤버들 
	@OneToMany(mappedBy = "novel", cascade = CascadeType.REMOVE)
	private List<LikeNovel> MembersLikeNovel = new ArrayList<>();
	
	// novel <-> episode
	@OneToMany(mappedBy = "novel", cascade = CascadeType.REMOVE)
	private List<Episode> episodes = new ArrayList<Episode>();
}
