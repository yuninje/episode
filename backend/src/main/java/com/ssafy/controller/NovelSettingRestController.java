package com.ssafy.controller;

import com.ssafy.model.dto.novelSetting.NovelSettingResponseDto;
import com.ssafy.model.dto.novelSetting.NovelSettingSaveRequestDto;
import com.ssafy.model.dto.novelSetting.NovelSettingUpdateRequestDto;
import com.ssafy.model.service.NovelSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = { "8. NovelSetting" })
@RestController
@RequestMapping("/novel-settings")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class NovelSettingRestController {

    @Autowired
    NovelSettingService novelSettingService;

    @ApiOperation("전체 소설설정 조회")
    @GetMapping()
    ResponseEntity<Map<String, Object>> getNovelSettings(){
        List<NovelSettingResponseDto> novelSettingResponseDtos = novelSettingService.getNovelSettings();
        novelSettingResponseDtos.forEach(novelSettingResponseDto -> System.out.println(novelSettingResponseDto));
        return handleSuccess(novelSettingResponseDtos);
    }

    @ApiOperation("소설설정 한 개의 정보를 조회")
    @GetMapping("/{novelSettingPk}")
    ResponseEntity<Map<String, Object>> getNovelSetting(@PathVariable int novelSettingPk) {
        return handleSuccess(novelSettingService.getNovelSetting(novelSettingPk));
    }

    @ApiOperation("소설설정 등록")
    @PostMapping()
    ResponseEntity<Map<String, Object>> registNovelSetting(@RequestBody NovelSettingSaveRequestDto requestDto) {
        return handleSuccess(novelSettingService.registNovelSetting(requestDto));
    }

    @ApiOperation("소설설정 수정")
    @PutMapping("/{novelSettingPk}")
    ResponseEntity<Map<String, Object>> updateNovelSetting(@PathVariable int novelSettingPk, @RequestBody NovelSettingUpdateRequestDto novelSettingUpdateRequestDto) {
        return handleSuccess(novelSettingService.updateNovelSetting(novelSettingPk, novelSettingUpdateRequestDto));
    }
    @ApiOperation("소설설정 삭제")
    @DeleteMapping("/{novelSettingPk}")
    ResponseEntity<Map<String, Object>> deleteNovelSetting(@PathVariable int novelSettingPk) {
        novelSettingService.deleteNovelSetting(novelSettingPk);
        return handleSuccess("해시태그 삭제 완료");
    }

    @ApiOperation("한 소설의 소설설정 가져오기 | 반환값 : data {  Novel, List<NovelSettings> }")
    @GetMapping("/novels/{novelPk}")
    ResponseEntity<Map<String, Object>> getNovelSettingsByNovel(@PathVariable int novelPk){
        return handleSuccess(novelSettingService.getNovelSettingByNovel(novelPk));
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
