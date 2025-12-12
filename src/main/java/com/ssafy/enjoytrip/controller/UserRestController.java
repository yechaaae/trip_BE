package com.ssafy.enjoytrip.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.service.UserService;


import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController // 1. view를 던져주는 것이 아닌 json 데이터 리턴
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	
	
	// TODO : 추가할 api : 회원 정보 조회, 회원 정보 수정, 회원 탈퇴, 비밀번호 변경, 아이디 찾기, 비밀번호 찾기
	// TODO : 
	// TODO : 에러 처리 세분화 논의 필요
	private final UserService userService;
	
	
	/**
	 * 1. 회원가입 (POST)
	 * Vue에서 JSON 형태로 { userId: "...", ... } 를 보냅니다.
	 * @return 성공 시 201 Created, 실패 시 500 Error
	 */
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserDto userDto){
		log.debug("회원가입 요청 데이터: {}",userDto);
		
		try {
			userService.joinUser(userDto); // 201 : Created(자원이 성공적으로 생성됨)
			return new ResponseEntity<Void>(HttpStatus.CREATED);
			
		}catch(Exception e){
			e.printStackTrace();
			return exceptionHandling(e);
	    }
	}
	
	
	
	/**
	 * 2. 로그인 (POST)
	 * Vue에서 JSON으로 { userId: "...", password: "..." } 전송
	 * @return 성공 시 UserDto(JSON) + 200 OK
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpSession session){
		log.debug("로그인 요청 : {}", userDto);
		try {
			UserDto loginUser = userService.loginUser(userDto.getUserId(), userDto.getPassword());
			if(loginUser != null) {
				// 세션처리 
				session.setAttribute("userInfo", loginUser);
				// 비밀번호는 굳이 넘길 필요 없음
				loginUser.setPassword(null);
				
				// 로그인한 유저 정보 넘기기
				return new ResponseEntity<UserDto>(loginUser,HttpStatus.OK);
				
			}else {
				// 로그인 실패
				// 204 No content 또는 401 Unauthorized
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
			
				
		
		}catch(Exception e){
			e.printStackTrace();
			return exceptionHandling(e);
		}
	
	}
		
	
	
	
	
	
	/**
	 * 3. 로그아웃 (GET)
	 * 세션 무효화 후 성공 메시지 리턴
	 */
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session){
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	
	/**
	 * 4. 아이디 중복 체크 (GET)
	 * URL 예시: /user/idcheck/ssafy1234
	 */
	@GetMapping("/idcheck/{userId}")
	public ResponseEntity<?> idCheck(@PathVariable("userId") String userId){
		log.debug("중복 체크 요청 아이디:{}", userId);
		try {
			int cnt = userService.idCheck(userId);
			return new ResponseEntity<Integer>(cnt,HttpStatus.OK); // 0 or 1 리턴
		}catch(Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	
	
	// 공통 예외 처리 메서드
	
		private ResponseEntity<String> exceptionHandling(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
