package com.ssafy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genre_pk")
	private Integer genrePk;
	
	@Column(name = "genre_name", length = 30, unique = true, nullable = false)
	private String genreName;

	@ManyToMany(cascade = CascadeType.PERSIST)
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

	public void beforeDelete(){
		// 소설
		for(Novel novel : novels){
			novel.getGenres().remove(this);
		}
		novels = new ArrayList<>();
	}
}
