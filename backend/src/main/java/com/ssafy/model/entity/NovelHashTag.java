package com.ssafy.model.entity;

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
@Table(name = "novel_hashtag")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NovelHashTag {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "novel_hashtag_pk")
	private Integer novelHashTagPk;
	
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
	
	@ManyToOne
	@JoinColumn(name = "hashtag_pk", nullable = false)
	private HashTag hashtag;
}
