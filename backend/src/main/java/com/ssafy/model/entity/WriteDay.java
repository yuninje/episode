package com.ssafy.model.entity;

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
@Table(name = "writeday")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WriteDay {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wrd_pk")
	private Integer wrdPk;
	
	@Column(name = "wrd_day", nullable = false)
	private Integer wrdDay;
}
