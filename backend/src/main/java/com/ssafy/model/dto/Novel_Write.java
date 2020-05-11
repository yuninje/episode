package com.ssafy.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "novel_write")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Novel_Write {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int novel_write_pk;
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
	@ManyToOne
	@JoinColumn(name = "wrd_pk", nullable = false)
	private WriteDay writeDay;
}
