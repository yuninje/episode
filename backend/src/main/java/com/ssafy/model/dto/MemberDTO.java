package com.ssafy.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.model.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private int memPk;
	private String memId;
	private String memEmail;
	private String memPw;
	private String memNick;
	private String memPhone;
	private String memBirth;
	private boolean memGender;
	private List<CommentDTO> likeCommentDTOs = new ArrayList<>();
}
