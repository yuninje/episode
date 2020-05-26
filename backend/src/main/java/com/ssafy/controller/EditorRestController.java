package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"8. Editor"})
@RestController
@RequestMapping("/editor")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class EditorRestController {

    @ApiOperation("에피소드 등록 | 소설 update 시간 갱신")
    @PostMapping()
    public void getGrammar(@RequestBody HashMap<String, Object> map) {
    	
        System.out.println(map);
    }
}
