package com.ssafy.model.dto.comment;

import com.ssafy.model.entity.Comment;
import com.ssafy.model.entity.Episode;
import com.ssafy.model.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String commentContent;
    private int memPk;
    private int episodePk;

    @Builder
    public CommentSaveRequestDto(String commentContent, int memPk, int episodePk) {
        this.commentContent = commentContent;
        this.memPk = memPk;
        this.episodePk = episodePk;
    }

    public Comment toEntity(Member member, Episode episode){
        return Comment.builder()
                .member(member)
                .episode(episode)
                .commentContent(commentContent)
                .commentCreatedAt(ZonedDateTime.now())
                .build();
    }
}
