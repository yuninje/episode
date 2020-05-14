package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.service.NovelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "5. Search" })
@RestController
@RequestMapping("/search")
public class SearchRestController {
	@Autowired
	NovelService nService;
	
	@ApiOperation("해당 문자열을 포함하고 있는 제목을 가진 소설들 조회")
	@GetMapping("/novel-name={novelName}")
	ResponseEntity<Map<String, Object>> getNovelsByName(@PathVariable String novelName, 
			@PageableDefault(page=0, size=10) Pageable pageable) {
		return handleSuccess(nService.getNovelsByName(novelName, pageable));
	}
	
	@ApiOperation("해당 문자열을 포함하고 있는 닉네임 소유의 소설들 조회")
	@GetMapping("/author={memNick}")
	ResponseEntity<Map<String, Object>> getNovelsByNick(@PathVariable String memNick, 
			@PageableDefault(page=0, size=10) Pageable pageable) {
		return handleSuccess(nService.getNovlesByNick(memNick, pageable));
	}
	
	@ApiOperation("해당 문자열을 포함하고 있는 제목 혹은 닉네임 소유의 소설들 조회")
	@GetMapping("/autho-or-novel={word}")
	ResponseEntity<Map<String, Object>> getNovelsByNameOrNick(@PathVariable String word, 
			@PageableDefault(page=0, size=10) Pageable pageable) {
		return handleSuccess(nService.getNovelsByNameOrNick(word, pageable));
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
