package com.ssafy.controller;

import java.util.HashMap;
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
import com.ssafy.model.dto.Novel;
import com.ssafy.model.service.NovelService;

import io.swagger.annotations.Api;

@Api(tags = { "2. Novel" })
@RestController
@RequestMapping("/novels")
public class NovelRestController {
	@Autowired
	NovelService nService;

	@GetMapping()
	ResponseEntity<Map<String, Object>> getNovels(){
		return handleSuccess(nService.getNovels());
	}

	@PostMapping()
	ResponseEntity<Map<String, Object>> registNovel(@RequestBody Novel novel){
		nService.registNovel(novel);
		return handleSuccess("소설 생성 완료");
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
