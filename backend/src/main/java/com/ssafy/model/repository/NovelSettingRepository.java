package com.ssafy.model.repository;

import com.ssafy.model.entity.Novel;
import com.ssafy.model.entity.NovelSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelSettingRepository extends JpaRepository<NovelSetting, Integer> {
    List<NovelSetting> findByNovel(Novel novel);
}
