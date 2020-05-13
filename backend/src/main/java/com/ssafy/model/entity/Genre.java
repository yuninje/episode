package com.ssafy.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	

//	// genre <-> NovelGenre >> 장르에 속한 소설
//	// 이 장르에 속한 소설
//	@OneToMany(mappedBy = "genre") 
//	private List<NovelGenre> novels = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "novel_genre",
			joinColumns = @JoinColumn(name = "genre_pk"),
			inverseJoinColumns = @JoinColumn(name = "novel_pk") 
			)	
	private List<Novel> novels = new ArrayList<Novel>();
	
	public void addGenreOfNovel(Novel novel) {
		novels.add(novel);
		novel.getGenres().add(this);
	}
	
	public void removeGenreOfNovel(Novel novel) {
		novels.remove(novel);
		novel.getGenres().remove(this);
	}
}
