package com.ssafy.controller;

import com.ssafy.model.dto.name.NameSaveRequestDto;
import com.ssafy.model.dto.name.NameUpdateRequestDto;
import com.ssafy.model.service.NameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = { "Name" })
@RestController
@RequestMapping("/names")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class NameRestController {
    @Autowired
    NameService nameService;

    @ApiOperation("전체 이름 조회")
    @GetMapping()
    ResponseEntity<Map<String, Object>> getNames(){
        return handleSuccess(nameService.getNames());
    }

    @ApiOperation("PK로 이름 한 개의 정보를 조회 | 존재하지 않으면 에러 처리")
    @GetMapping("/{namePk}")
    ResponseEntity<Map<String, Object>> getName(@PathVariable int namePk) {
        return handleSuccess(nameService.getName(namePk));
    }

    @ApiOperation("문자열로 이름 한 개의 정보를 조회 | 존재하지 않으면 데이터 생성 후 리턴")
    @GetMapping("/name={name}")
    ResponseEntity<Map<String, Object>> getNameByName(@PathVariable String name) {
        return handleSuccess(nameService.getNameByName(name));
    }

    @ApiOperation("이름 등록")
    @PostMapping()
    ResponseEntity<Map<String, Object>> registName(@RequestBody NameSaveRequestDto requestDto) {
        return handleSuccess(nameService.registName(requestDto));
    }

    @ApiOperation("이름 삭제 | 사용할 일이 없음")
    @DeleteMapping("/{namePk}")
    ResponseEntity<Map<String, Object>> deleteName(@PathVariable int namePk) {
        nameService.deleteName(namePk);
        return handleSuccess("이름 삭제 완료");
    }

    @ApiOperation("이름 수정")
    @PutMapping("/{namePk}")
    ResponseEntity<Map<String, Object>> updateName(@PathVariable int namePk, @RequestBody NameUpdateRequestDto requestDto) {
        return handleSuccess(nameService.updateName(namePk, requestDto));
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