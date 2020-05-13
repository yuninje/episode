package com.ssafy.model.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.model.entity.Genre;
import com.ssafy.model.entity.Novel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovelGenreDTO {
	private int novelGenrePk;
	private Genre genre;
	private Novel novel;
}
