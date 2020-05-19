package com.ssafy.controller;

import com.ssafy.model.dto.genre.GenreSaveRequestDto;
import com.ssafy.model.dto.genre.GenreUpdateRequestDto;
import com.ssafy.model.service.GenreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
	ResponseEntity<Map<String, Object>> registGenre(@RequestBody GenreSaveRequestDto requestDto) {
		gService.registGenre(requestDto);
		return handleSuccess("장르 생성 완료");
	}

	@ApiOperation("장르 삭제")
	@DeleteMapping("/{genrePk}")
	ResponseEntity<Map<String, Object>> registGenre(@PathVariable int genrePk) {
		gService.deleteGenre(genrePk);
		return handleSuccess("장르 삭제 완료");
	}

	@ApiOperation("장르 수정")
	@PutMapping("/{genrePk}")
	ResponseEntity<Map<String, Object>> updateGenre(@PathVariable int genrePk, @RequestBody GenreUpdateRequestDto requestDto) {
		gService.updateGenre(genrePk, requestDto);
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
