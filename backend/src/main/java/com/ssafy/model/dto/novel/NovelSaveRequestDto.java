package com.ssafy.model.dto.novel;

import java.util.List;

import com.ssafy.model.entity.Novel;
import com.ssafy.model.repository.MemberRepository;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelSaveRequestDto{
    private int memberPk;
    private String novelName;
    private String novelIntro;
    private String novelImage;
    private boolean novelLimit;
    private boolean novelOpen;
    private int novelStatus;
    private boolean novelOnly;
    private List<Integer> genres;
    private List<String> hashTags;

    @Builder
    public NovelSaveRequestDto(
            int memberPk,
            String novelName,
            String novelIntro,
            String novelImage,
            boolean novelLimit,
            boolean novelOpen,
            int novelStatus,
            boolean novelOnly) {

        this.memberPk = memberPk;
        this.novelName = novelName;
        this.novelIntro = novelIntro;
        this.novelImage = novelImage;
        this.novelLimit = novelLimit;
        this.novelOpen = novelOpen;
        this.novelStatus = novelStatus;
        this.novelOnly = novelOnly;
    }

    public Novel toEntity(MemberRepository memberRepository){
        return Novel.builder()
                .member(memberRepository.findById(memberPk)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다.")))
                .novelName(novelName)
                .novelIntro(novelIntro)
                .novelImage(novelImage)
                .novelLimit(novelLimit)
                .novelOpen(novelOpen)
                .novelStatus(novelStatus)
                .novelOnly(novelOnly)
                .build();
    }
}