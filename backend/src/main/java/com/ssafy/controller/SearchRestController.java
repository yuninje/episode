package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.service.NovelService;
import com.ssafy.model.service.SearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "5. Search" })
@RestController
@RequestMapping("/search")
public class SearchRestController {
	@Autowired
	NovelService nService;
	@Autowired
	SearchService sService;
	
	@ApiOperation("검색")
	@GetMapping("/{type}/{word}")
	ResponseEntity<Map<String, Object>> getNovelsBySearchWord(
			@ApiParam(allowableValues="all, author_name, novel_name, hashtag")
			@PathVariable String type,
			@PathVariable String word, 
			@PageableDefault(page=0, size=10) Pageable pageable,
			@ApiParam(value="-- : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
				allowableValues="likes, recommends, view")
			@RequestParam(required=false) String sort,
			@ApiParam("검색한 유저의 memPk")
			@RequestParam(required=false, defaultValue="0") int memPk,
			@ApiParam("장르 pk")
			@RequestParam(required=false, defaultValue="0") int genrePk) {
		return handleSuccess(nService.getNovelsBySearchWord(type, word, pageable, sort, memPk, genrePk));
	}
	@ApiOperation("오늘로부터 하루 이내의 검색어 순위")
	@GetMapping("/real-time-search")
	ResponseEntity<Map<String, Object>> getRealTimeSearch() {
		return handleSuccess(sService.getRealTimeSearch());
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
