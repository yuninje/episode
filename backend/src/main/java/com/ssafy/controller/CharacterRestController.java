package com.ssafy.controller;

import com.ssafy.model.dto.character.CharacterSaveRequestDto;
import com.ssafy.model.dto.character.CharacterUpdateRequestDto;
import com.ssafy.model.service.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = { "Character" })
@RestController
@RequestMapping("/characters")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class CharacterRestController {
	@Autowired
	CharacterService cService;
	
	@ApiOperation("캐릭터 전체 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getCharacters(){
		return handleSuccess(cService.getCharacters());
	}
	
	@ApiOperation("한 소설의 캐릭터 조회")
	@GetMapping("/novelPk={novelPk}")
	ResponseEntity<Map<String, Object>> getCharactersByNovel(@PathVariable int novelPk){
		return handleSuccess(cService.getCharactersByNovel(novelPk));
	}
	
	@ApiOperation("캐릭터 하나 조회")
	@GetMapping("/{characterPk}")
	ResponseEntity<Map<String, Object>> getCharacter(@PathVariable int characterPk){
		return handleSuccess(cService.getCharacter(characterPk));
	}
	
	@ApiOperation("캐릭터 생성")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registCharacter(@RequestBody CharacterSaveRequestDto requestDto){
		return handleSuccess(cService.registCharacter(requestDto));
	}
	
	@ApiOperation("캐릭터 수정")
	@PutMapping("/{characterPk}")
	ResponseEntity<Map<String, Object>> updateCharacter(@PathVariable int characterPk, @RequestBody CharacterUpdateRequestDto requestDto){
		return handleSuccess(cService.updateCharacter(characterPk, requestDto));
	}
	
	@ApiOperation("캐릭터 삭제")
	@DeleteMapping("/{characterPk}")
	ResponseEntity<Map<String, Object>> deleteCharacter(@PathVariable int characterPk){
		cService.deleteCharacter(characterPk);
		return handleSuccess("캐릭터 삭제 완료");
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
