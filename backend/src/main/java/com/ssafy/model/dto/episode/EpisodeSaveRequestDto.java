package com.ssafy.model.dto.episode;

import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Novel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EpisodeSaveRequestDto {
    private int novelPk;
    private String episodeTitle;
    private String episodeContent;
    private String episodeWriter;

    @Builder
    public EpisodeSaveRequestDto(int novelPk, String episodeTitle, String episodeContent, String episodeWriter) {
        this.novelPk = novelPk;
        this.episodeTitle = episodeTitle;
        this.episodeContent = episodeContent;
        this.episodeWriter = episodeWriter;
    }

    public Episode toEntity(Novel novel){
        return Episode.builder()
                .novel(novel)
                .episodeTitle(episodeTitle)
                .episodeContent(episodeContent)
                .episodeWriter(episodeWriter)
                .episodeCreatedAt(LocalDateTime.now())
                .episodeView(0)
                .build();
    }
}
