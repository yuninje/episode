package com.ssafy.model.dto.episode;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EpisodeUpdateRequestDto {
    private String episodeTitle;
    private String episodeContent;
    private String episodeWriter;

    @Builder
    public EpisodeUpdateRequestDto(String episodeTitle, String episodeContent, String episodeWriter) {
        this.episodeTitle = episodeTitle;
        this.episodeContent = episodeContent;
        this.episodeWriter = episodeWriter;
    }
}
