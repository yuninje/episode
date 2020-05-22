package com.ssafy.model.dto.comment;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String commentContent;
    @Builder
    public CommentUpdateRequestDto(String commentContent) {
        this.commentContent = commentContent;
    }
}


