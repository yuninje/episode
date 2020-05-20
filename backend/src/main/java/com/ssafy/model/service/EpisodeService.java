package com.ssafy.model.service;

import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.episode.EpisodeSaveRequestDto;
import com.ssafy.model.dto.episode.EpisodeUpdateRequestDto;
import com.ssafy.model.entity.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface EpisodeService {
	// 기본적인 CRUD
	EpisodeResponseDto registEpisode(EpisodeSaveRequestDto requestDto);
	Page<EpisodeResponseDto> getEpisodes(Pageable pageable);
	EpisodeResponseDto getEpisode(int episodePk);
	EpisodeResponseDto updateEpisode(int episodePk, EpisodeUpdateRequestDto requestDto);
	void deleteEpisode(int episodePk);

	Map getEpisodesByNovel(int novelPk, Pageable pageable);
	void deleteEpisode(Episode episode);
	void deleteAllEpisode();

}
