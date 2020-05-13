package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.GenreDTO;
import com.ssafy.model.service.GenreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "4. Genre" })
@RestController
@RequestMapping("/genres")
public class GenreRestController {
	@Autowired
	GenreService gService;

	@ApiOperation("전체 장르 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getGenres(){
		return handleSuccess(gService.getGenres());
	}
	
	@ApiOperation("장르 한 개의 정보를 조회")
	@GetMapping("/{genrePk}")
	ResponseEntity<Map<String, Object>> getGenre(@PathVariable int genrePk) {
		return handleSuccess(gService.getGenre(genrePk));
	}
	
	@ApiOperation("장르 등록")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registGenre(@RequestBody GenreDTO genreDTO) {
		gService.registGenre(genreDTO);
		return handleSuccess("장르 생성 완료");
	}

	@ApiOperation("장르 삭제")
	@DeleteMapping("/{genrePk}")
	ResponseEntity<Map<String, Object>> registGenre(@PathVariable int genrePk) {
		gService.deleteGenre(genrePk);
		return handleSuccess("장르 삭제 완료");
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
