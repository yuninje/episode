package com.ssafy.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

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
	private Integer searchPk;
	
	@Column(name = "search_word", length = 30, nullable = false)
	private String searchWord;

	@CreatedDate
	@Column(name = "search_created_at", nullable = false)
	private Date searchCreatedAt;

	// search <-> member >> N : 1 관계
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = true)
	private Member member;
	
	@Transient
	private Long searchCnt;

	public Search(String searchWord, Long searchCnt) {
		super();
		this.searchWord = searchWord;
		this.searchCnt = searchCnt;
	}
}
