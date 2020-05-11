package com.ssafy.model.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "search")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Search {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "search_pk")
	private int searchPk;
	
	@Column(name = "search_word", length = 30, nullable = false)
	private String searchWord;

	@CreatedDate
	@Column(name = "search_created_at", nullable = false)
	private Date searchCreatedAt;

	// search <-> member >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;
}
