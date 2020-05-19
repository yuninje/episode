package com.ssafy.controller;

import com.ssafy.model.dto.episode.EpisodeSaveRequestDto;
import com.ssafy.model.dto.episode.EpisodeUpdateRequestDto;
import com.ssafy.model.service.EpisodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = {"3. Episode"})
@RestController
@RequestMapping("/episodes")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class EpisodeRestController {
    @Autowired
    EpisodeService eService;

    @ApiOperation("에피소드 전체 조회")
    @GetMapping()
    ResponseEntity<Map<String, Object>> getEpisodes(
            @PageableDefault(page=0, size=10) Pageable pageable) {
        return handleSuccess(eService.getEpisodes(pageable));
    }

    @ApiOperation("에피소드 한 개의 정보를 조회 | 에피소드 조회수 + 1, 소설 통합 조회수 + 1")
    @GetMapping("/{episodePk}")
    ResponseEntity<Map<String, Object>> getEpisode(@PathVariable int episodePk) {
        return handleSuccess(eService.getEpisode(episodePk));
    }

    @ApiOperation("해당 소설의 에피소드 전체 조회")
    @GetMapping("/novel-pk={novelPk}")
    ResponseEntity<Map<String, Object>> getEpisodesByName(
            @PageableDefault(page=0, size=10) Pageable pageable,
            @PathVariable int novelPk) {
        return handleSuccess(eService.getEpisodesByNovel(novelPk, pageable));
    }

    @ApiOperation("에피소드 등록")
    @PostMapping()
    ResponseEntity<Map<String, Object>> registEpisode(@RequestBody EpisodeSaveRequestDto requestDto) {
        return handleSuccess(eService.registEpisode(requestDto));
    }

    @ApiOperation("에피소드 한 개를 정보를 수정")
    @PutMapping("/{episodePk}")
    ResponseEntity<Map<String, Object>> updateEpisode(@PathVariable int episodePk, @RequestBody EpisodeUpdateRequestDto requestDto) {
        return handleSuccess(eService.updateEpisode(episodePk, requestDto));
    }

    @ApiOperation("에피소드 삭제")
    @DeleteMapping("/{episodePk}")
    ResponseEntity<Map<String, Object>> deleteEpisode(@PathVariable int episodePk) {
        eService.deleteEpisode(episodePk);
        return handleSuccess("pk : " + episodePk + " 에피소드 삭제 완료");
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
