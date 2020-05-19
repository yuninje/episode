package com.ssafy.model.entity;

//@Entity
//@Table(name = "like_episode")
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//public class LikeEpisode { // 에피소드 좋아요
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "like_episode_pk")
//	private Integer likeEpisodePk;
//
//	// like_episode <-> member
//	@ManyToOne
//	@JoinColumn(name = "mem_pk", nullable = false)
//	private Member member;
//
//	// like_episode <-> episode
//	@ManyToOne
//	@JoinColumn(name = "episode_pk", nullable = false)
//	private Episode episode;
//}