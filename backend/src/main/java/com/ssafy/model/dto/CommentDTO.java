package com.ssafy.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	private Integer commentPk;
	private String commentContent;
	private Date commentCreatedAt;
	private EpisodeDTO episodeDTO;
	private MemberDTO memberDTO;
	private List<Author> likedMembers = new ArrayList<>();
}
