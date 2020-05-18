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

import com.ssafy.model.dto.Auth;
import com.ssafy.model.dto.MemberDTO;
import com.ssafy.model.entity.Member;
import com.ssafy.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "1. Member" })
@RestController
@RequestMapping("/members")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class MemberRestController {
	@Autowired
	MemberService mService;
	
	@ApiOperation("전체 멤버 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getMembers(){
		return handleSuccess(mService.getMembers());
	}
	
	@ApiOperation("회원가입 | /check/{key}를 통해 중복, 유효성 검사를 마친 후라고 판단하여 무조건 회원가입이 성공했다고 간주")
	@PostMapping("")
	ResponseEntity<Map<String, Object>> doRegist(@RequestBody MemberDTO member){
		mService.regist(member);
		
		return handleSuccess("회원 가입에 성공하였습니다.");
	}
	
	@ApiOperation("멤버 한 명의 정보를 조회")
	@GetMapping("/{memPk}")
	ResponseEntity<Map<String, Object>> getMember(@PathVariable int memPk){
		return handleSuccess(mService.getMember(memPk));
	}
	
	@ApiOperation("멤버 한 명의 정보를 수정 | 변경된 멤버 객체 반환")
	@PutMapping("/{memPk}")
	ResponseEntity<Map<String, Object>> updateMember(@PathVariable int memPk, @RequestBody MemberDTO member){
		return handleSuccess(mService.updateMember(memPk, member));
	}
	
	@ApiOperation("멤버 삭제 | 반환되는 data는 비어져있음")
	@DeleteMapping("/{memPk}")
	ResponseEntity<Map<String, Object>> deleteMember(@PathVariable int memPk){
		mService.deleteMember(memPk);
		
		return handleSuccess("");
	}

	@ApiOperation("로그인 | 성공 시 해당 멤버 객체 반환(패스워드는 비워져 있음)")
	@PostMapping("/login")
	ResponseEntity<Map<String, Object>> doLogin(@RequestBody Auth auth){
		MemberDTO member = mService.login(auth);
		member.setMemPw("");

		return handleSuccess(member);
	}
	
	@ApiOperation("아이디/닉네임/이메일/전화번호 중복 검사, 비밀번호 유효성 검사 | 성공 시 data는 비어있으며(state는 ok) 실패시 data에는 에러 문구가 들어감(state는 fail)")
	@PostMapping("/check/{key}")
	ResponseEntity<Map<String, Object>> check(
			@ApiParam("1: 아이디\n2: 닉네임\n3: 비밀번호\n4: 이메일\n5: 전화번호") @PathVariable int key, 
			@ApiParam("스웨거에서 테스트 시 양 옆의 쌍따옴표도 문자로 간주하므로 제거해야 함") @RequestBody String input){
		boolean res = false;
		String fail[] = {"중복된 아이디입니다.", "중복된 닉네임입니다.", "올바르지 않은 비밀번호입니다.", 
				"중복된 이메일입니다.", "중복된 전화번호입니다."};
		
		switch(key) {
		case 1:			// 아이디
			res = mService.checkId(input);
			break;
		case 2:			// 닉네임
			res = mService.checkNick(input);
			break;
		case 3:			// 비밀번호
			res = mService.checkPw(input);
			break;
		case 4:			// 이메일
			res = mService.checkEmail(input);
			break;
		case 5:			// 전화번호
			res = mService.checkPhone(input);
			break;
		}
		
		if(res) return handleSuccess("");
		else return handleFail(fail[key - 1], HttpStatus.OK);
	}
	
	@ApiOperation("좋아요 요청 | 성공시 data에 문자열이 반환")
	@GetMapping("/{memPk}/{objectType}/{objectPk}/{flag}") // 임시 URL
	ResponseEntity<Map<String, Object>> doLike(
			@ApiParam("요청한 회원의 PK") @PathVariable int memPk, 
			@ApiParam("요청한 대상의 PK") @PathVariable int objectPk,
			@ApiParam("요청한 대상의 타입 | 2 : 소설, 3 : 에피소드, 6 : 댓글") @PathVariable int objectType, 
			@ApiParam("좋아요 / 좋아요 취소 요청 | true : 좋아요, false : 좋아요 취소") @PathVariable boolean flag ){
		mService.doLike(memPk, objectPk, objectType, flag);			
		if(flag)
			return handleSuccess("좋아요 완료");
		else
			return handleSuccess("좋아요 취소 완료");
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
