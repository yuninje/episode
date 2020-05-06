package com.ssafy.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Member")
@Getter
@Setter
@ToString
@Builder
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mem_pk;
	@Column(length = 100, nullable = false)
	private String mem_id;
	@Column(length = 200, nullable = false)
	private String mem_email;
	@Column(length = 512, nullable = false)
	private String mem_pw;
	@Column(length = 100, nullable = false)
	private String mem_nick;
	@Column(length = 20, nullable = false)
	private String mem_phone;
	@Column(nullable = false)
	private String mem_birth;
	@Column(nullable = false)
	private boolean mem_gender;
}
