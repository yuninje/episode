package com.ssafy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

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

	// character <-> novel
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;

	// character <-> person >>  N : 1 관계
	@ManyToOne
	@JoinColumn(name = "person_pk", nullable = true)
	private Person person;

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
	private Set<Additional> characterMore = new LinkedHashSet<>();
	
	@Column(name = "character_image", length = 100, nullable = true)
	private String characterImage;

	
	// character <-> who >> 1: N 관계
	@OneToMany(mappedBy = "who", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private Set<Relation> whos = new LinkedHashSet<>();
	
	// character <-> whom >> 1: N 관계
	@OneToMany(mappedBy = "whom", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private Set<Relation> whoms = new LinkedHashSet<>();


	// 인물 연결
	@Builder
	public Character(Integer characterPk, String characterName, String characterAge, Boolean characterGender,
					 String characterRole, String characterJob, String characterPersonallity, String characterSignificant,
					 Set<Additional> characterMore, String characterImage, Novel novel, Person person) {
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
		this.person = person;
	}

	public Character update(String characterName, String characterAge, Boolean characterGender, String characterRole,
			String characterJob, String characterPersonallity, String characterSignificant,
			Set<Additional> characterMore, String characterImage, Person person) {
		this.characterName = characterName;
		this.characterAge = characterAge;
		this.characterGender = characterGender;
		this.characterRole = characterRole;
		this.characterJob = characterJob;
		this.characterPersonallity = characterPersonallity;
		this.characterSignificant = characterSignificant;
		this.characterMore = characterMore;
		this.characterImage = characterImage;

		if(this.person == person){
			// 패스
		}else{
			if(this.person == null && person != null){	// person 넣기
				this.person = person;
				this.person.getCharacters().add(this);
			}else if(this.person != null && person == null){	// person 취소
				this.person.getCharacters().remove(this);
				this.person = null;
			}else{										// 다른 person 넣기
				this.person.getCharacters().remove(this);
				this.person = person;
				this.person.getCharacters().add(this);
			}
		}

		return this;
	}

	public void removePerson(){
		person = null;
	}

	public void beforeDelete(){

	}
}
