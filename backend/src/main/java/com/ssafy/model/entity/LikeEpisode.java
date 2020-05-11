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
@Table(name = "like_episode")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeEpisode { // 에피소드 좋아요 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_episode_pk")
	private int likeEpisodePk;
	
	// like_episode <-> member
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;

	// like_episode <-> episode
	@ManyToOne
	@JoinColumn(name = "episode_pk", nullable = false)
	private Episode episode;
}