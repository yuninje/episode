package com.ssafy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.Member;
import com.ssafy.model.repository.MemberRepository;
import com.ssafy.model.service.MemberService;

import io.swagger.annotations.Api;

@Api(tags = { "1. Member" })
@RestController
@RequestMapping("/members")
public class MemberRestController {
	@Autowired
	MemberService mService;
	
	@GetMapping()
	ResponseEntity<Map<String, Object>> getMembers(){
		return handleSuccess(mService.getMembers());
	}

	@PostMapping("/login")
	ResponseEntity<Map<String, Object>> doLogin(@RequestBody Auth auth){
		Member member = mService.login(auth);

		return handleSuccess(member);
	}
	
	@PostMapping("/regist")
	ResponseEntity<Map<String, Object>> doRegist(@RequestBody Member member){
		mService.regist(member);
		
		return handleSuccess("회원 가입에 성공하였습니다.");
	}
	
	@PostMapping("/check/{key}")
	ResponseEntity<Map<String, Object>> check(@PathVariable int key, @RequestBody String input){
		boolean res = false;
		String fail[] = {"중복된 아이디입니다.", "중복된 닉네임입니다.", "올바르지 않은 비밀번호입니다.", 
				"중복된 이메일입니다.", "중복된 전화번호입니다."};
		
		switch(key) {
		case 1:			// 아이디
			res = mService.checkId(input);
			break;
		case 2:			// 닉네임
			res = mService.checkNick(input);
			break;
		case 3:			// 비밀번호
			break;
		case 4:			// 이메일
			res = mService.checkEmail(input);
			break;
		case 5:			// 전화번호
			res = mService.checkPhone(input);
			break;
		}
		
		if(res) return handleSuccess("");
		else return handleFail(fail[key - 1], HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
