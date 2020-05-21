package com.ssafy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
	@Column(name = "hashtag_pk")
	private Integer hashTagPk;
	
	@Column(name = "hashtag_name", length = 30, nullable = false)
	private String hashTagName;


	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "novel_hashtag",
			joinColumns = @JoinColumn(name = "hashtag_pk"),
			inverseJoinColumns = @JoinColumn(name = "novel_pk") 
			)	
	private List<Novel> novels = new ArrayList<Novel>();

	@Builder
	public HashTag(String hashTagName) {
		this.hashTagName = hashTagName;
	}
}
