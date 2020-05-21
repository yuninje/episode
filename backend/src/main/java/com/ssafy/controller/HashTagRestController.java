package com.ssafy.controller;

import com.ssafy.model.dto.hashtag.HashTagSaveRequestDto;
import com.ssafy.model.service.HashTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = { "7. HashTag" })
@RestController
@RequestMapping("/hashTag")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class HashTagRestController {
	@Autowired
	HashTagService hService;
	
	@ApiOperation("전체 해시태그 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getHashTags(){
		return handleSuccess(hService.getHashTags());
	}
	
	@ApiOperation("해시태그 한 개의 정보를 조회")
	@GetMapping("/{hashTagPk}")
	ResponseEntity<Map<String, Object>> getHashTag(@PathVariable int hashTagPk) {
		return handleSuccess(hService.getHashTag(hashTagPk));
	}
	
	@ApiOperation("해시태그 등록")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registHashTag(@RequestBody HashTagSaveRequestDto requestDto) {
		hService.registHashTag(requestDto);
		return handleSuccess("해시태그 생성 완료");
	}

	@ApiOperation("해시태그 삭제")
	@DeleteMapping("/{hashTagPk}")
	ResponseEntity<Map<String, Object>> registHashTag(@PathVariable int hashTagPk) {
		hService.deleteHashTag(hashTagPk);
		return handleSuccess("해시태그 삭제 완료");
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
