package com.ssafy.model.entity;

import com.ssafy.model.dto.novelSetting.NovelSettingUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "novel_setting")
@Entity
public class NovelSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int novelSettingPk;

    @ManyToOne
    @JoinColumn(name = "novel_pk", nullable = false)
    private Novel novel;

    @Column(name = "novel_setting_name" , length = 30, nullable = false)
    public String novelSettingName;

    @Column(name = "novel_setting_text", nullable = false, columnDefinition="TEXT")
    private String novelSettingText;

    @Builder
    public NovelSetting(Novel novel, String novelSettingName, String novelSettingText) {
        this.novel = novel;
        this.novelSettingName = novelSettingName;
        this.novelSettingText = novelSettingText;
    }

    public void update(NovelSettingUpdateRequestDto novelSettingUpdateRequestDto){
        this.novelSettingName = novelSettingUpdateRequestDto.getNovelSettingName();
        this.novelSettingText = novelSettingUpdateRequestDto.getNovelSettingText();
    }


    public void beforeDelete(){

    }
}