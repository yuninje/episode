package com.ssafy.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dto.EpisodeDTO;
import com.ssafy.model.dto.NovelDTO;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.EpisodeRepository;

@Service
public class EpisodeServiceImpl implements EpisodeService{
	
	@Autowired
	EpisodeRepository eRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public void registEpisode(EpisodeDTO episodeDTO) {
		Episode episode = modelMapper.map(episodeDTO, Episode.class);
		eRepo.save(episode);
	}

	@Override
	public List<EpisodeDTO> getEpisodes() {
		List<Episode> episodes = eRepo.findAll();
		List<EpisodeDTO> episodeDTOs =
				episodes.stream().map(episode -> {
					EpisodeDTO episodeDTO = modelMapper.map(episode, EpisodeDTO.class);
					Novel novel = episode.getNovel();
					NovelDTO novelDTO = modelMapper.map(novel, NovelDTO.class);
					episodeDTO.setNovelDTO(novelDTO);
					return episodeDTO;
				})
				.collect(Collectors.toList());
		return episodeDTOs;
	}

	@Override
	public EpisodeDTO getEpisode(int episodePk) {
		Episode episode = eRepo.findById(episodePk).orElse(null);
		
		EpisodeDTO episodeDTO = modelMapper.map(episode, EpisodeDTO.class);
		Novel novel = episode.getNovel();
		NovelDTO novelDTO = modelMapper.map(novel, NovelDTO.class);
		episodeDTO.setNovelDTO(novelDTO);
		
		return episodeDTO;
	}

	@Override
	public void updateEpisode(int episodePk, EpisodeDTO episodeDTO) {
		eRepo.updateEpisode(
				episodePk, 
				episodeDTO.getEpisodeTitle(),
				episodeDTO.getEpisodeContent(),
				episodeDTO.getEpisodeWriter(),
				episodeDTO.getEpisodeView()
				);
	}

	@Override
	public void deleteEpisode(int episodePk) {
		eRepo.deleteById(episodePk);
	}

	@Override
	public List<EpisodeDTO> getEpisodesByNovel(int novelPk) {
		List<Episode> episodes = eRepo.findByNovelPk(novelPk);
		List<EpisodeDTO> episodeDTOs =
				episodes.stream().map(episode -> modelMapper.map(episode, EpisodeDTO.class))
				.collect(Collectors.toList());
		return episodeDTOs;
	}
	
}
