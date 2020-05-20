package com.ssafy.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@Column(name = "hashtag_pk")
	private Integer hashTagPk;
	
	@Column(name = "hashtag_name", length = 30, nullable = false)
	private String hashTagName;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "novel_hashtag",
			joinColumns = @JoinColumn(name = "hashtag_pk"),
			inverseJoinColumns = @JoinColumn(name = "novel_pk") 
			)	
	private List<Novel> novels = new ArrayList<Novel>();
}
