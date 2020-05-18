package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.EpisodeDTO;
import com.ssafy.model.service.EpisodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "3. Episode" })
@RestController
@RequestMapping("/episodes")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class EpisodeRestController {
	@Autowired
	EpisodeService eService;

	@ApiOperation("에피소드 전체 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getEpisodes(){
		return handleSuccess(eService.getEpisodes());
	}
	
	@ApiOperation("에피소드 한 개의 정보를 조회 | 에피소드 조회수 + 1, 소설 통합 조회수 + 1")
	@GetMapping("/{episodePk}")
	ResponseEntity<Map<String, Object>> getEpisode(@PathVariable int episodePk) {
		return handleSuccess(eService.getEpisode(episodePk));
	}
	
	@ApiOperation("해당 소설의 에피소드 전체 조회")
	@GetMapping("/novel-pk={novelPk}")
	ResponseEntity<Map<String, Object>> getEpisodesByName(@PathVariable int novelPk) {
		return handleSuccess(eService.getEpisodesByNovel(novelPk));
	}

	@ApiOperation("에피소드 등록")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registEpisode(@RequestBody EpisodeDTO episodeDTO) {
		eService.registEpisode(episodeDTO);
		return handleSuccess("에피소드 생성 완료");
	}
	
	@ApiOperation("에피소드 한 개를 정보를 수정")
	@PutMapping("/{episodePk}")
	ResponseEntity<Map<String, Object>> updateEpisode(@PathVariable int episodePk, @RequestBody EpisodeDTO episodeDTO) {
		eService.updateEpisode(episodePk, episodeDTO);
		return handleSuccess("episodePk "+ episodePk + " 업데이트 완료");
	}
	
	@ApiOperation("에피소드 삭제")
	@DeleteMapping("/{episodePk}")
	ResponseEntity<Map<String, Object>> deleteEpisode(@PathVariable int episodePk) {
		eService.deleteEpisode(episodePk);
		return handleSuccess("pk : "+episodePk + " 에피소드 삭제 완료");
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
