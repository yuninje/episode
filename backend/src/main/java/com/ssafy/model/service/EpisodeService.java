package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.EpisodeDTO;

public interface EpisodeService {
	// 기본적인 CRUD
	void registEpisode(EpisodeDTO episodeDTO);
	List<EpisodeDTO> getEpisodes();
	EpisodeDTO getEpisode(int episodePk);
	void updateEpisode(int episodePk, EpisodeDTO episodeDTO);
	void deleteEpisode(int episodePk);
	
	List<EpisodeDTO> getEpisodesByNovel(int novelPk);
}
