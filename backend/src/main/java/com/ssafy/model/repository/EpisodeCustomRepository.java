package com.ssafy.model.repository;

import com.ssafy.model.entity.Episode;

public interface EpisodeCustomRepository {
	Episode[] findEpisodesByEpisodePk(int episodePk, int novelPk);
}
