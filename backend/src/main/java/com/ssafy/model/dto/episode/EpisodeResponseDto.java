package com.ssafy.model.dto.episode;

import com.ssafy.model.dto.novel.NovelResponseDto;
import com.ssafy.model.entity.Episode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EpisodeResponseDto {
    private int episodePk;
    private String episodeTitle;
    private String episodeContent;
    private LocalDateTime episodeCreatedAt;
    private String episodeWriter;
    private int episodeView;
    private NovelResponseDto novel;

    public EpisodeResponseDto(Episode episode){
        this.episodePk = episode.getEpisodePk();
        this.episodeTitle = episode.getEpisodeTitle();
        this.episodeContent = episode.getEpisodeContent();
        this.episodeCreatedAt = episode.getEpisodeCreatedAt();
        this.episodeWriter = episode.getEpisodeWriter();
        this.episodeView = episode.getEpisodeView();
        this.novel = new NovelResponseDto(episode.getNovel());
    }

    @Builder
    public EpisodeResponseDto(int episodePk, String episodeTitle, String episodeContent, LocalDateTime episodeCreatedAt, String episodeWriter, int episodeView, NovelResponseDto novel) {
        this.episodePk = episodePk;
        this.episodeTitle = episodeTitle;
        this.episodeContent = episodeContent;
        this.episodeCreatedAt = episodeCreatedAt;
        this.episodeWriter = episodeWriter;
        this.episodeView = episodeView;
        this.novel = novel;
    }
}
