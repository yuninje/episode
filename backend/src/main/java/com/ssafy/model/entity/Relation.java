package com.ssafy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "relation")
@Getter
@Setter
@NoArgsConstructor
public class Relation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "relation_pk")
	private Integer relationPk;
	
	@Column(name = "relation_name", length = 50, nullable = false)
	private String relationrName;
	
	@Column(name = "relation_color", length = 50, nullable = false)
	private String relationrColor;
	
	@Column(name = "relation_arrow_kinds", length = 50, nullable = false)
	private String relationArrowKinds;
	
	@ManyToOne
	@JoinColumn(name = "relation_who", referencedColumnName = "character_pk", nullable = false)
	private Character who;
	
	@ManyToOne
	@JoinColumn(name = "relation_whom", referencedColumnName = "character_pk", nullable = false)
	private Character whom;

	@Builder
	public Relation(Integer relationPk, String relationrName, String relationrColor, String relationArrowKinds,
			Character who, Character whom) {
		this.relationPk = relationPk;
		this.relationrName = relationrName;
		this.relationrColor = relationrColor;
		this.relationArrowKinds = relationArrowKinds;
		this.who = who;
		this.whom = whom;
	}
}
