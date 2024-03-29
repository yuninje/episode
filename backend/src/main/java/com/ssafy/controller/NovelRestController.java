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
import java.util.Map;

@Api(tags = { "Novel" })
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
			@ApiParam(value="updated : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
				allowableValues="updated, likes, recommends, view", required=true)
			@RequestParam String sort){
		return handleSuccess(nService.getNovels(pageable, sort));
	}
	
	@ApiOperation("소설 한 개의 정보를 조회")
	@GetMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> getNovel(@PathVariable int novelPk) {
		return handleSuccess(nService.getNovel(novelPk));
	}
	
	@ApiOperation("해당 장르에 속한 소설들 조회")
	@GetMapping("/genre-pk={genrePk}")
	ResponseEntity<Map<String, Object>> getNovelsByGenre(@PathVariable int genrePk,
														 @PageableDefault(page=0, size=10) Pageable pageable,
														 @ApiParam(value="updated : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
																 allowableValues="updated, likes, recommends, view", required=true)
														 @RequestParam String sort) {
		return handleSuccess(nService.getNovelsByGenre(genrePk, pageable,sort));
	}

	@ApiOperation("소설 등록")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registNovel(@RequestBody NovelSaveRequestDto requestDto) {
		return handleSuccess(nService.registNovel(requestDto));
	}
	
	@ApiOperation("소설 한 개의 정보를 수정 | genrePks : 체크된 모든 장르의 PK 값을 리스트로 보내면 됨, hashTagStrs : 해시태그들의 String 값을 리스트로 보내면 됨")
	@PutMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> updateNovel(@PathVariable int novelPk, @RequestBody NovelUpdateRequestDto requestDto) {
		return handleSuccess(nService.updateNovel(novelPk, requestDto));
	}

	@ApiOperation("소설 이미지 정보 수정 | genrePks : 체크된 모든 장르의 PK 값을 리스트로 보내면 됨, hashTagStrs : 해시태그들의 String 값을 리스트로 보내면 됨")
	@PutMapping("/{novelPk}/image")
	ResponseEntity<Map<String, Object>> updateNovel(@PathVariable int novelPk, @RequestBody String imageUrl) {
		return handleSuccess(nService.updateNovelImage(novelPk, imageUrl));
	}
	
	@ApiOperation("소설 삭제 | 성공하면 String 문자열 반환")
	@DeleteMapping("/{novelPk}")
	ResponseEntity<Map<String, Object>> deleteNovel(@PathVariable int novelPk) {
		nService.deleteNovel(novelPk);
		return handleSuccess("소설 삭제 성공");
	}

	@ApiOperation("해당 멤버의 소설 조회")
	@GetMapping("/member-pk={memPk}") // url 바꿔야함
	ResponseEntity<Map<String, Object>> getNovelByMember(@PathVariable int memPk, 
			@PageableDefault(page=0, size=10) Pageable pageable,
			@ApiParam(value="updated : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
				allowableValues="updated, likes, recommends, view", required=true)
			@RequestParam String sort) {
		return handleSuccess(nService.getNovelByMember(memPk, pageable, sort));
	}
	
	@ApiOperation("조회수 기준으로 top 100 구하기")
	@GetMapping("/top100")
	ResponseEntity<Map<String, Object>> getTop100(){
		return handleSuccess(nService.getTop100());
	}
	
	@ApiOperation("느낌별 소설")
	@GetMapping("/feel/{type}")
	ResponseEntity<Map<String, Object>> getFeelNovels(
			@ApiParam(value="1 : 사이다 같은 통쾌함\n"
					+ "2 : 역대급 먼치킨 주인공\n"
					+ "3 : 로맨스 치사량 주의\n"
					+ "4 : 인생을 바로 살다\n"
					+ "5 : 범인은 바로\n"
					+ "6 : 저세상 텐션 꿀재미",
				allowableValues="1, 2, 3, 4, 5, 6", required=true)
			@PathVariable int type, 
			@PageableDefault(page=0, size=10) Pageable pageable,
			@ApiParam(value="updated : 업데이트순\nlikes : 선호작순\nrecommends : 추천순\nview : 조회수순",
				allowableValues="updated, likes, recommends, view", required=true)
			@RequestParam String sort, 
			@ApiParam("장르 pk")
			@RequestParam(required=false, defaultValue="0") int genrePk) {
		return handleSuccess(nService.getFeelNovels(type, pageable, sort, genrePk));
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
