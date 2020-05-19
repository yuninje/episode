package com.ssafy.model.dto.comment;

import com.ssafy.model.entity.Comment;
import com.ssafy.model.repository.EpisodeRepository;
import com.ssafy.model.repository.MemberRepository;
import lombok.*;

import java.time.LocalDate;

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

    public Comment toEntity(MemberRepository memberRepository, EpisodeRepository episodeRepository){
        return Comment.builder()
                .member(memberRepository.findById(memPk)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다.")))
                .episode(episodeRepository.findById(episodePk)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 에피소드입니다.")))
                .commentContent(commentContent)
                .commentCreatedAt(LocalDate.now())
                .build();
    }
}
