package com.ssafy.model.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequestDto {
	private String memEmail;
	private String memPw;
	private String memNick;
	private String memPhone;
	private String memBirth;
	private boolean memGender;
	
//	@Builder
//	public MemberUpdateRequestDto(String memId, String memEmail, String memPw, String memNick, String memPhone,
//			String memBirth, boolean memGender) {
//		super();
//		this.memId = memId;
//		this.memEmail = memEmail;
//		this.memPw = memPw;
//		this.memNick = memNick;
//		this.memPhone = memPhone;
//		this.memBirth = memBirth;
//		this.memGender = memGender;
//	}
}