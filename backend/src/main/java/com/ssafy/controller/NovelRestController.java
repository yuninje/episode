package com.ssafy.controller;

import com.ssafy.model.dto.novel.NovelSaveRequestDto;
import com.ssafy.model.dto.novel.NovelUpdateRequestDto;
import com.ssafy.model.service.NovelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = { "2. Novel" })
@RestController
@RequestMapping("/novels")
public class NovelRestController {
	@Autowired
	NovelService nService;

	@ApiOperation("소설 전체 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getNovels(
			@PageableDefault(page=0, size=10) Pageable pageable, 
			@ApiParam("비어있을 경우 업데이트순으로, likes 입력시 선호작순으로, recommends 입력시 추천순으로 내림차순 정렬")
			@RequestParam(required=false, defaultValue="") String sort){
		return handleSuccess(nService.getNovels(pageable, sort));
	}
	
	@ApiOperation("소설 한 개의 정보를 조회")
	@GetMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> getNovel(@PathVariable int novelPk) {
		return handleSuccess(nService.getNovel(novelPk));
	}
	
	@ApiOperation("해당 장르에 속한 소설들 조회")
	@GetMapping("/genre-pk={genrePk}")
	ResponseEntity<Map<String, Object>> getNovelsByGenre(@PathVariable int genrePk) {
		return handleSuccess(nService.getNovelsByGenre(genrePk));
	}

	@ApiOperation("소설 등록")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registNovel(@RequestBody NovelSaveRequestDto requestDto) {
		nService.registNovel(requestDto);
		return handleSuccess("소설 생성 완료");
	}
	
	@ApiOperation("소설 한 개의 정보를 수정 | 변경된 소설 객체 반환")
	@PutMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> updateNovel(@PathVariable int novelPk, @RequestBody NovelUpdateRequestDto requestDto) {
		return handleSuccess(nService.updateNovel(novelPk, requestDto));
	}
	
	@ApiOperation("소설 삭제 | 반환되는 data는 비어져있음")
	@DeleteMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> deleteNovel(@PathVariable int novelPk) {
		nService.deleteNovel(novelPk);
		return handleSuccess("");
	}	

	@ApiOperation("소설의 장르 업데이트 | 체크된 모든 장르의 PK 값을 배열이나 리스트로 보내면 됨")
	@PutMapping("/genre/{novelPk}") // url 바꿔야함
	ResponseEntity<Map<String, Object>> updateGenreOfNovel(@PathVariable int novelPk, @RequestBody List<Integer> genrePks) {
		nService.updateGenreOfNovel(novelPk, genrePks);
		return handleSuccess("소설의 장르 업데이트 성공");
	}
	
	@ApiOperation("해당 멤버의 소설 조회")
	@GetMapping("/member-pk={memPk}") // url 바꿔야함
	ResponseEntity<Map<String, Object>> getNovelByMember(@PathVariable int memPk, 
			@PageableDefault(page=0, size=10) Pageable pageable,
			@ApiParam("비어있을 경우 업데이트 순서대로, likes 입력시 선호작 순서대로 정렬")
			@RequestParam(required=false, defaultValue="") String sort) {
		return handleSuccess(nService.getNovelByMember(memPk, pageable, sort));
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
