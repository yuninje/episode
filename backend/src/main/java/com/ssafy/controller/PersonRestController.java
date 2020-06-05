package com.ssafy.controller;

import com.ssafy.model.dto.person.PersonSaveRequestDto;
import com.ssafy.model.dto.person.PersonUpdateRequestDto;
import com.ssafy.model.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = { "Person" })
@RestController
@RequestMapping("/persons")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class PersonRestController {
    @Autowired
    PersonService personService;

    @ApiOperation("전체 인물 조회")
    @GetMapping()
    ResponseEntity<Map<String, Object>> getPersons(){
        return handleSuccess(personService.getPersons());
    }

    @ApiOperation("인물 한 개의 정보를 조회")
    @GetMapping("/{personPk}")
    ResponseEntity<Map<String, Object>> getPerson(@PathVariable int personPk) {
        return handleSuccess(personService.getPerson(personPk));
    }

    @ApiOperation("이름으로 전체 인물 조회")
    @GetMapping("/name={name}")
    ResponseEntity<Map<String, Object>> getPersonByName(@PathVariable String name) {
        return handleSuccess(personService.getPersonByName(name));
    }

    @ApiOperation("인물 등록")
    @PostMapping()
    ResponseEntity<Map<String, Object>> registPerson(@RequestBody PersonSaveRequestDto requestDto) {
        return handleSuccess(personService.registPerson(requestDto));
    }

    @ApiOperation("인물 삭제")
    @DeleteMapping("/{personPk}")
    ResponseEntity<Map<String, Object>> deletePerson(@PathVariable int personPk) {
        personService.deletePerson(personPk);
        return handleSuccess("인물 삭제 완료");
    }

    @ApiOperation("인물 수정")
    @PutMapping("/{personPk}")
    ResponseEntity<Map<String, Object>> updatePerson(@PathVariable int personPk, @RequestBody PersonUpdateRequestDto requestDto) {
        return handleSuccess(personService.updatePerson(personPk, requestDto));
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