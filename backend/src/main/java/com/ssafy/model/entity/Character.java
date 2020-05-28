package com.ssafy.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "character_")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Character {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "character_pk")
	private Integer characterPk;
	
	@Column(name = "character_name", length = 50, nullable = false)
	private String characterName;
	
	@Column(name = "character_age", length = 30, nullable = false)
	private String characterAge;
	
	@Column(name = "character_gender", nullable = false, columnDefinition="TINYINT(1)")
	private Boolean characterGender;
	
	@Column(name = "character_role", length = 30, nullable = true)
	private String characterRole;
	
	@Column(name = "character_job", length = 30, nullable = true)
	private String characterJob;
	
	@Column(name = "character_personallity", length = 30, nullable = true)
	private String characterPersonallity;
	
	@Column(name = "character_significant", length = 100, nullable = true)
	private String characterSignificant;
	
	// http://redutan.github.io/2018/05/29/ddd-values-on-jpa
	@Convert(converter = CharacterAdditionalConverter.class)
	@Column(name = "character_more", nullable = true, length = 4000)
	private Set<Additional> characterMore = new HashSet<>();
	
	@Column(name = "character_image", length = 100, nullable = true)
	private String characterImage;
	
	// character <-> novel
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
	
	// character <-> who >> 1: N 관계
	@OneToMany(mappedBy = "who", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Relation> whos = new ArrayList<Relation>();
	
	// character <-> whom >> 1: N 관계
	@OneToMany(mappedBy = "whom", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Relation> whoms = new ArrayList<Relation>();

	@Builder
	public Character(Integer characterPk, String characterName, String characterAge, Boolean characterGender,
			String characterRole, String characterJob, String characterPersonallity, String characterSignificant,
			Set<Additional> characterMore, String characterImage, Novel novel) {
		this.characterPk = characterPk;
		this.characterName = characterName;
		this.characterAge = characterAge;
		this.characterGender = characterGender;
		this.characterRole = characterRole;
		this.characterJob = characterJob;
		this.characterPersonallity = characterPersonallity;
		this.characterSignificant = characterSignificant;
		this.characterMore = characterMore;
		this.characterImage = characterImage;
		this.novel = novel;
	}
}
