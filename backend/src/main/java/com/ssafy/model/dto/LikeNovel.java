package com.ssafy.model.dto;

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
@Table(name = "like_novel")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeNovel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int likeNovelPk;
	
	
	// like_novel <-> member
	@ManyToOne
	@JoinColumn(name="mem_pk")
	private Member member;
	

	// like_novel <-> novel
	@ManyToOne
	@JoinColumn(name="novel_pk")
	private Novel novel;
}