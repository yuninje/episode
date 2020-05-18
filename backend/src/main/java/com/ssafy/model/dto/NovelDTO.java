package com.ssafy.model.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.ssafy.model.entity.Genre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovelDTO{
	private int novelPk;
	private String novelName;
	private String novelIntro;
	private String novelImage;
	private boolean novelLimit;
	private boolean novelOpen;
	private int novelStatus;
	private boolean novelOnly;
	private Long novelView = 0L;
	private Date novelUpdatedAt;
	private Author member;
	private List<String> genreList;
	private long likes;
}
