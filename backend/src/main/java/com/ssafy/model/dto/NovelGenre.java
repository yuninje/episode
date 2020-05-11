package com.ssafy.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "novel_genre")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelGenre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "novel_genre_pk")
	private int novelGenrePk;
	
	@ManyToOne
	@JoinColumn(name = "genre_pk", nullable = false)
	private Genre genre;
	
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
}
