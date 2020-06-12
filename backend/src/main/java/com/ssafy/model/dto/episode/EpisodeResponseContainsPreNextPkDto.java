package com.ssafy.model.dto.episode;

import java.time.ZonedDateTime;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.Episode;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EpisodeResponseContainsPreNextPkDto {
	private int episodePk;
    private String episodeTitle;
    private String episodeContent;
    private ZonedDateTime episodeCreatedAt;
    private String episodeWriter;
    private Long episodeView;
    private NovelResponseDto novel;
    private int preEpisodePk;
    private int nextEpisodePk;

    public EpisodeResponseContainsPreNextPkDto(Episode episode){
        this.episodePk = episode.getEpisodePk();
        this.episodeTitle = episode.getEpisodeTitle();
        this.episodeContent = episode.getEpisodeContent();
        this.episodeCreatedAt = episode.getEpisodeCreatedAt();
        this.episodeWriter = episode.getEpisodeWriter();
        this.episodeView = episode.getEpisodeView();
        this.novel = new NovelResponseDto(episode.getNovel());
    }

    @Builder
    public EpisodeResponseContainsPreNextPkDto(int episodePk, String episodeTitle, String episodeContent, ZonedDateTime episodeCreatedAt, String episodeWriter, Long episodeView, NovelResponseDto novel) {
        this.episodePk = episodePk;
        this.episodeTitle = episodeTitle;
        this.episodeContent = episodeContent;
        this.episodeCreatedAt = episodeCreatedAt;
        this.episodeWriter = episodeWriter;
        this.episodeView = episodeView;
        this.novel = novel;
    }
}
