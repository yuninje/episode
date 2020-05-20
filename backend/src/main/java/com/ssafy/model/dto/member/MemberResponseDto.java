package com.ssafy.model.dto.member;

import com.ssafy.model.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberResponseDto {
	private Integer memPk;
	private String memId;
	private String memEmail;
	private String memNick;
	private String memPhone;
	private String memBirth;
	private Boolean memGender;
	
	public MemberResponseDto(Member member) {
		this.memPk = member.getMemPk();
		this.memId = member.getMemId();
		this.memEmail = member.getMemEmail();
		this.memNick = member.getMemNick();
		this.memPhone = member.getMemPhone();
		this.memBirth = member.getMemBirth();
		this.memGender = member.getMemGender();
	}
	
    @Builder
	public MemberResponseDto(Integer memPk, String memId, String memEmail, String memNick, String memPhone,
			String memBirth, Boolean memGender) {
		this.memPk = memPk;
		this.memId = memId;
		this.memEmail = memEmail;
		this.memNick = memNick;
		this.memPhone = memPhone;
		this.memBirth = memBirth;
		this.memGender = memGender;
	}
}