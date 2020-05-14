package com.ssafy.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDTO {
	private int episodePk;
	private String episodeTitle;
	private String episodeContent;
	private Date episodeCreatedAt;
	private String episodeWriter;
	private int episodeView;
	private NovelDTO novelDTO;
}