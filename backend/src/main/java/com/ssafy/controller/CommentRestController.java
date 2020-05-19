package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.model.dto.comment.CommentSaveRequestDto;
import com.ssafy.model.dto.comment.CommentUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.ssafy.model.dto.CommentDTO;
import com.ssafy.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "6. Comment" })
@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class CommentRestController {

	@Autowired
	CommentService cService;

	@ApiOperation("댓글 전체 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getComments(@PageableDefault(page=0, size=10) Pageable pageable){
		return handleSuccess(cService.getComments(pageable));
	}
	
	@ApiOperation("댓글 하나 조회")
	@GetMapping("/{commentPk}")
	ResponseEntity<Map<String, Object>> getComment(@PathVariable int commentPk){
		return handleSuccess(cService.getComment(commentPk));
	}
	
	@ApiOperation("댓글 생성")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registComment(@RequestBody CommentSaveRequestDto requestDto){
		return handleSuccess(cService.registComment(requestDto));
	}
	
	@ApiOperation("댓글 삭제")
	@DeleteMapping("/{commentPk}")
	ResponseEntity<Map<String, Object>> deleteComment(@PathVariable int commentPk){
		cService.deleteComment(commentPk);
		return handleSuccess("댓글 삭제 완료");
	}
	
	@ApiOperation("댓글 수정")
	@PutMapping("/{commentPk}")
	ResponseEntity<Map<String, Object>> updateComment(@PathVariable int commentPk, @RequestBody CommentUpdateRequestDto requestDto){
		return handleSuccess(cService.updateComment(commentPk, requestDto));
	}

	@ApiOperation("에피소드에 대한 댓글 조회")
	@GetMapping("/episode-pk={episodePk}")
	ResponseEntity<Map<String, Object>> getCommentByEpisode(@PathVariable int episodePk, @PageableDefault(page=0, size=10) Pageable pageable){
		return handleSuccess(cService.getCommentByEpisode(episodePk, pageable));
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
