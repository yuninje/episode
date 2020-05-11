package com.ssafy.model.dto;

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

import org.hibernate.annotations.ColumnDefault;
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
	@Column(name = "episode_pk")
	private int episodePk;
	
	@Column(name = "episode_title", length = 30, nullable = false)
	private String episodeTitle;
	
	@Column(name = "episode_content", nullable = false, columnDefinition="TEXT")
	private String episodeContent;

	@CreatedDate
	@Column(name = "episode_created_at", nullable = false)
	private Date episodeCreatedAt;
	
	@Column(name = "episode_writer", length = 100, nullable = true)
	private String episodeWriter;
	
	@Column(name = "episode_view", nullable = false)
	@ColumnDefault("0")
	private int episodeView;

	// episode <-> novel
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
	
	// member <-> comment
	@OneToMany(mappedBy = "episode", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<Comment>();

	// episode <-> like_episode >> 에피소드 좋아요
	// 이 에피소드를 좋아하는 멤버들 
	@OneToMany(mappedBy = "episode")
	private List<LikeEpisode> MembersLikeEpisode = new ArrayList<>();
}
