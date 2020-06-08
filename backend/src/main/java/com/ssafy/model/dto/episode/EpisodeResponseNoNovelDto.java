package com.ssafy.model.dto.episode;

import com.ssafy.model.entity.Episode;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EpisodeResponseNoNovelDto {
    private int episodePk;
    private String episodeTitle;
    private String episodeContent;
    private ZonedDateTime episodeCreatedAt;
    private String episodeWriter;
    private Long episodeView;

    public EpisodeResponseNoNovelDto(Episode episode){
        this.episodePk = episode.getEpisodePk();
        this.episodeTitle = episode.getEpisodeTitle();
        this.episodeContent = episode.getEpisodeContent();
        this.episodeCreatedAt = episode.getEpisodeCreatedAt();
        this.episodeWriter = episode.getEpisodeWriter();
        this.episodeView = episode.getEpisodeView();
    }

    @Builder
    public EpisodeResponseNoNovelDto(int episodePk, String episodeTitle, String episodeContent, ZonedDateTime episodeCreatedAt, String episodeWriter, Long episodeView) {
        this.episodePk = episodePk;
        this.episodeTitle = episodeTitle;
        this.episodeContent = episodeContent;
        this.episodeCreatedAt = episodeCreatedAt;
        this.episodeWriter = episodeWriter;
        this.episodeView = episodeView;
    }
}
