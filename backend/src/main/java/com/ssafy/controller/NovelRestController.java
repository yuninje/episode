package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.NovelDTO;
import com.ssafy.model.entity.Member;
import com.ssafy.model.entity.Novel;
import com.ssafy.model.service.NovelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "2. Novel" })
@RestController
@RequestMapping("/novels")
public class NovelRestController {
	@Autowired
	NovelService nService;

	@ApiOperation("소설 전체 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getNovels(@PageableDefault(page=0, size=10) Pageable pageable){
		return handleSuccess(nService.getNovels(pageable));
	}
	
	@ApiOperation("소설 한 개의 정보를 조회")
	@GetMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> getNovel(@PathVariable int novelPk) {
		return handleSuccess(nService.getNovel(novelPk));
	}

	@ApiOperation("소설 등록")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registNovel(@RequestBody NovelDTO novel) {
		nService.registNovel(novel);
		return handleSuccess("소설 생성 완료");
	}
	
	@ApiOperation("소설 한 개의 정보를 수정 | 변경된 소설 객체 반환")
	@PutMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> updateNovel(@PathVariable int novelPk, @RequestBody NovelDTO novel) {
		return handleSuccess(nService.updateNovel(novelPk, novel));
	}
	
	@ApiOperation("소설 삭제 | 반환되는 data는 비어져있음")
	@DeleteMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> deleteNovel(@PathVariable int novelPk) {
		nService.deleteNovel(novelPk);
		return handleSuccess("");
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
