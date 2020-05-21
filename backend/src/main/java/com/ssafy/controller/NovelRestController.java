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
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class NovelRestController {
	@Autowired
	NovelService nService;

	@ApiOperation("소설 전체 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getNovels(
			@PageableDefault(page=0, size=10) Pageable pageable, 
			@ApiParam(value="-- : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
					allowableValues="likes, recommends, view")
			@RequestParam(required=false) String sort){
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
		return handleSuccess(nService.registNovel(requestDto));
	}
	
	@ApiOperation("소설 한 개의 정보를 수정 | 변경된 소설 객체 반환")
	@PutMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> updateNovel(@PathVariable int novelPk, @RequestBody NovelUpdateRequestDto requestDto) {
		return handleSuccess(nService.updateNovel(novelPk, requestDto));
	}
	
	@ApiOperation("소설 삭제 | 성공하면 String 문자열 반환")
	@DeleteMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> deleteNovel(@PathVariable int novelPk) {
		nService.deleteNovel(novelPk);
		return handleSuccess("소설 삭제 성공");
	}	

	@ApiOperation("소설의 장르 업데이트 | 체크된 모든 장르의 PK 값을 배열이나 리스트로 보내면 됨")
	@PutMapping("/genre/{novelPk}") // url 바꿔야함
	ResponseEntity<Map<String, Object>> updateGenreOfNovel(@PathVariable int novelPk, @RequestBody List<Integer> genrePks) {
		return handleSuccess(nService.updateGenreOfNovel(novelPk, genrePks));
	}
	
	@ApiOperation("해당 멤버의 소설 조회")
	@GetMapping("/member-pk={memPk}") // url 바꿔야함
	ResponseEntity<Map<String, Object>> getNovelByMember(@PathVariable int memPk, 
			@PageableDefault(page=0, size=10) Pageable pageable,
			@ApiParam(value="-- : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
				allowableValues="likes, recommends, view")
			@RequestParam(required=false) String sort) {
		return handleSuccess(nService.getNovelByMember(memPk, pageable, sort));
	}
	
	@ApiOperation("조회수 기준으로 top 100 구하기")
	@GetMapping("/top100")
	ResponseEntity<Map<String, Object>> getTop100(){
		return handleSuccess(nService.getTop100());
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
