package com.ssafy.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "character")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@TypeDef(
//		name = "jsonb",
//	    typeClass = JsonBinaryType.class)
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
	
//	@Type(type = "jsonb")
//	@Column(name = "character_more", nullable = true, columnDefinition = "jsonb")
//	private List<Additional> characterMore = new ArrayList<>();
	
	@Column(name = "character_image", length = 100, nullable = true)
	private String characterImage;
	
	// character <-> novel
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
}
