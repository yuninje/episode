package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.relation.RelationSaveRequestDto;
import com.ssafy.model.service.RelationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "9. Relation" })
@RestController
@RequestMapping("/relations")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class RelationRestController {
	@Autowired
	RelationService rService;
	
	@ApiOperation("한 소설의 인물 관계 조회")
	@GetMapping("/novelPk={novelPk}")
	ResponseEntity<Map<String, Object>> getRelationsByNovel(@PathVariable int novelPk){
		return handleSuccess(rService.getRelationsByNovel(novelPk));
	}
	
	@ApiOperation("인물 관계 생성")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registRelation(@RequestBody RelationSaveRequestDto requestDto){
		return handleSuccess(rService.registRelation(requestDto));
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
