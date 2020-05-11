package com.ssafy.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "episode")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Episode {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int episodePk;
	
	@Column(name="episode_title", length = 30, nullable = false)
	private String episodeTitle;
	
	@Column(name="episode_content", length = 512, nullable = false)
	private String episodeContent;

	@CreatedDate
	@Column(name="episode_created_at", nullable = false)
	private Date episodeCreatedAt;
	
	@Column(name="episode_writer", length = 100, nullable = false)
	private String episodeWriter;
	
	@Column(name="episode_view", nullable = false)
	private int episodeView;

	// episode <-> novel
	@ManyToOne
	@JoinColumn(name="novel_pk")
	private Novel novel;
	

	// episode <-> like_episode >> 에피소드 좋아요
	// 이 에피소드를 좋아하는 멤버들 
	@OneToMany(mappedBy="episode")
	private List<LikeEpisode> MembersLikeEpisode = new ArrayList<>();
	
}
