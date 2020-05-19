package com.ssafy.model.dto.comment;

import com.ssafy.model.dto.episode.EpisodeResponseNoNovelDto;
import com.ssafy.model.dto.member.MemberResponseDto;
import com.ssafy.model.entity.Comment;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentResponseDto {
    private int commentPk;
    private String commentContent;
    private MemberResponseDto member;
    private EpisodeResponseNoNovelDto episode; // 소설 안붙어있는거

    public CommentResponseDto(Comment comment) {
        this.commentPk = comment.getCommentPk();
        this.commentContent = comment.getCommentContent();
        this.member = new MemberResponseDto(comment.getMember());
        this.episode = new EpisodeResponseNoNovelDto(comment.getEpisode());
    }

    @Builder
    public CommentResponseDto(String commentContent, MemberResponseDto member, EpisodeResponseNoNovelDto episode) {
        this.commentContent = commentContent;
        this.member = member;
        this.episode = episode;
    }
}

