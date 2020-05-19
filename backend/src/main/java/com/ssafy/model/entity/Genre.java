package com.ssafy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genre_pk")
	private Integer genrePk;
	
	@Column(name = "genre_name", length = 30, nullable = false)
	private String genreName;
	

	// genre <-> NovelGenre >> 장르에 속한 소설
	// 이 장르에 속한 소설
//	@OneToMany(mappedBy = "genre") 
//	private List<NovelGenre> novels = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "novel_genre",
			joinColumns = @JoinColumn(name = "genre_pk"),
			inverseJoinColumns = @JoinColumn(name = "novel_pk") 
			)	
	private List<Novel> novels = new ArrayList<>();

	@Builder
	public Genre(String genreName) {
		this.genreName = genreName;
	}

	public Genre update(String genreName){
		this.genreName = genreName;
		return this;
	}
}
