package com.ssafy.model.dto.episode;

import com.ssafy.model.entity.Episode;
import com.ssafy.model.repository.NovelRepository;
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

    public Episode toEntity(NovelRepository novelRepository){
        return Episode.builder()
                .novel(novelRepository.findById(novelPk)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소설입니다.")))
                .episodeTitle(episodeTitle)
                .episodeContent(episodeContent)
                .episodeWriter(episodeWriter)
                .episodeCreatedAt(LocalDateTime.now())
                .episodeView(0)
                .build();
    }
}
