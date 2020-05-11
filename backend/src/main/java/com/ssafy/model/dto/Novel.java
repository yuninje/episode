package com.ssafy.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
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
@Table(name = "novel")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Novel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int novel_pk;
	@ManyToOne
	@JoinColumn(name = "mem_pk", nullable = false)
	private Member member;
	@Column(name = "novel_name", length = 30, nullable = false)
	private String novelName;
	@Column(name = "novel_intro", length = 100, nullable = true)
	private String novelIntro;
	@Column(name = "novel_image", length = 30, nullable = true)
	private String novelImage;
	@Column(name = "novel_limit", nullable = false)
	private boolean novelLimit;
	@Column(name = "novel_open", nullable = false)
	private boolean novelOpen;
	@Column(name = "novel_status", nullable = false, columnDefinition="TINYINT(1)")
	private int novelStatus;
	@Column(name = "novel_only", nullable = false)
	private boolean novelOnly;
	@Column(name = "novel_updatedAt", nullable = false)
	private Timestamp novelUpdatedAt;
}
