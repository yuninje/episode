package com.ssafy.model.service;

import com.ssafy.model.dto.episode.EpisodeResponseDto;
import com.ssafy.model.dto.episode.EpisodeResponseNoNovelDto;
import com.ssafy.model.dto.episode.EpisodeSaveRequestDto;
import com.ssafy.model.dto.episode.EpisodeUpdateRequestDto;
import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class EpisodeServiceImpl implements EpisodeService{

	@Autowired
	NovelRepository nRepo;
	@Autowired
	EpisodeRepository eRepo;
	
	@Override
	public EpisodeResponseDto registEpisode(EpisodeSaveRequestDto requestDto) {
		Episode episodeEntity = eRepo.save(requestDto.toEntity(nRepo));
		EpisodeResponseDto episode = new EpisodeResponseDto(episodeEntity);
		return episode;
	}

	@Override
	public Page<EpisodeResponseDto> getEpisodes(Pageable pageable) {
		Page<Episode> episodeEntityPage = eRepo.findAll(pageable);
		Page<EpisodeResponseDto> episodes =
				episodeEntityPage.map(episodeEntity -> new EpisodeResponseDto(episodeEntity));
		return episodes;
	}

	@Transactional
	@Override
	public EpisodeResponseDto getEpisode(int episodePk) {
		Episode episodeEntity = eRepo.findById(episodePk).orElseThrow(() ->
				new IllegalArgumentException("episode pk :  " + episodePk + "가 존재하지 않습니다."));

		EpisodeResponseDto episode = new EpisodeResponseDto(episodeEntity);
		return episode;
	}

	@Override
	public EpisodeResponseDto updateEpisode(int episodePk, EpisodeUpdateRequestDto requestDto) {
		Episode episodeEntity = eRepo.findById(episodePk).orElseThrow(() ->
				new IllegalArgumentException("episode pk :  " + episodePk + "가 존재하지 않습니다."));

		episodeEntity.update(
				requestDto.getEpisodeTitle(),
				requestDto.getEpisodeContent(),
				requestDto.getEpisodeWriter()
		);

		EpisodeResponseDto episode = new EpisodeResponseDto(eRepo.save(episodeEntity));
		return episode;
	}

	@Override
	public void deleteEpisode(int episodePk) {
		Episode episodeEntity = eRepo.findById(episodePk).orElseThrow(()->
				new IllegalArgumentException("comment " + episodePk + "가 존재하지 않습니다."));
		for(Member memberEntity : episodeEntity.getLikedMembers()){
			memberEntity.unLikeEpisode(episodeEntity);
		}
		eRepo.save(episodeEntity);
		eRepo.deleteById(episodePk);
	}

	@Override
	public Map getEpisodesByNovel(int novelPk, Pageable pageable) {
		Map data = new HashMap();

		Novel novelEntity = nRepo.findById(novelPk).orElseThrow(() ->
				new IllegalArgumentException("novel pk :  " + novelPk + "가 존재하지 않습니다."));
		NovelResponseDto novel = new NovelResponseDto(novelEntity);

		Page<Episode> episodeEntityPage = eRepo.findByNovel(novelEntity, pageable);
		Page<EpisodeResponseNoNovelDto> episodes =
				episodeEntityPage.map(episodeEntity -> new EpisodeResponseNoNovelDto(episodeEntity));

		data.put("novel", novel);
		data.put("episodes", episodes);
		return data;
	}
	
}
