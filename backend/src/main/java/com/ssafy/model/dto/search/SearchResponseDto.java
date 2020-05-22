package com.ssafy.model.dto.search;

import com.ssafy.model.entity.Search;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchResponseDto {
    private String searchWord;
    private Long searchCnt;

    public SearchResponseDto(Search search){
        this.searchWord = search.getSearchWord();
        this.searchCnt = search.getSearchCnt();
    }

    @Builder
    public SearchResponseDto(String searchWord, Long searchCnt){
        this.searchWord = searchWord;
        this.searchCnt = searchCnt;
    }
}
