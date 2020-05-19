package com.ssafy.model.dto.episode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EpisodeUpdateRequestDto {
    private String episodeTitle;
    private String episodeContent;
    private String episodeWriter;
}
