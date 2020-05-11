package com.ssafy.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hashtag")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HashTag {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hashtag_pk;
	@Column(name = "hashtag_name", length = 30, nullable = false)
	private String hashtagName;
}
