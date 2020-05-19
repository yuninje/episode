package com.ssafy.model.dto.member;

import com.ssafy.model.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
	private String memId;
	private String memEmail;
	private String memPw;
	private String memNick;
	private String memPhone;
	private String memBirth;
	private boolean memGender;
	
	@Builder
	public MemberSaveRequestDto(String memId, String memEmail, String memPw, String memNick, String memPhone,
			String memBirth, boolean memGender) {
		this.memId = memId;
		this.memEmail = memEmail;
		this.memPw = memPw;
		this.memNick = memNick;
		this.memPhone = memPhone;
		this.memBirth = memBirth;
		this.memGender = memGender;
	}
	
	public Member toEntity() {
		return Member.builder()
				.memId(memId)
				.memEmail(memEmail)
				.memPw(memPw)
				.memNick(memNick)
				.memPhone(memPhone)
				.memBirth(memBirth)
				.memGender(memGender)
				.build();
	}
}
