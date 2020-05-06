package com.ssafy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.Member;
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
		List<Member> members = mService.getMembers();
		return handleSuccess(members);
	}

	@PostMapping("/login")
	ResponseEntity<Map<String, Object>> doLogin(@RequestBody Auth auth){
		
		Member member = mService.login(auth);

		return handleSuccess(member);
	}
	@PostMapping("/regist")
	ResponseEntity<Map<String, Object>> doRegist(@RequestBody Member member){
		Boolean result = mService.regist(member);
		return handleSuccess(result);
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
