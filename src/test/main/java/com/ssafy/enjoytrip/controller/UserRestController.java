package com.ssafy.enjoytrip.controller;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
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
	
	/**
	 * 5. 회원 정보 조회 (GET)
	 * 로그인된 사용자의 상세 정보를 조회합니다.
	 * @param session 로그인 시 저장된 세션 정보를 사용합니다.
	 * @return 성공 시 UserDto(JSON) + 200 OK
	 */
	
	@GetMapping("/info")
	public ResponseEntity<?> getInfo(HttpSession session){
		UserDto loginUser = (UserDto) session.getAttribute("userInfo");
		
		if(loginUser == null) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		
		log.debug("회원 정보 조회 요청 {}",loginUser.getUserId());
		
		try {
			// DB에서 최신 회원 정보 조회(비밀번호 제외)
			UserDto userInfo = userService.getUserInfo(loginUser.getUserId());
			userInfo.setPassword(null); // 보안을 위해 비밀번호 제거
			return new ResponseEntity<UserDto>(userInfo,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	
	/**
	 * 6. 회원 정보 수정 (PUT)
	 * 닉네임, 이메일 등 ProfileEditPage.vue에서 필요한 정보 수정
	 * @param userDto 수정할 정보가 담긴 JSON
	 * @return 성공 시 200 OK
	 */
	
	@PutMapping
    public ResponseEntity<?> update(
            @RequestPart(value = "userDto") UserDto userDto, 
            @RequestPart(value = "file", required = false) MultipartFile file, 
            HttpSession session) {
        
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");
        if (loginUser == null) return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

        userDto.setUserId(loginUser.getUserId());

        try {
            // 파일이 있다면 업로드 처리
            if (file != null && !file.isEmpty()) {
                String saveFolder = "C:/ssafy/upload/"; // 실제 저장될 경로
                File folder = new File(saveFolder);
                if (!folder.exists()) folder.mkdirs(); // 폴더 없으면 생성

                // 파일명 중복 방지를 위한 UUID 처리
                String originalFileName = file.getOriginalFilename();
                String saveFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 서버에 파일 저장
                file.transferTo(new File(saveFolder + saveFileName));

                // DB에 저장할 경로 설정 (WebMvcConfig에서 설정한 경로 /upload/...)
                userDto.setProfileImg("/upload/" + saveFileName);
            } else {
                // 파일 변경이 없으면 기존 이미지 유지 로직이 필요할 수 있음
                // (Mapper XML에서 IF문으로 처리 권장)
            }

            userService.modifyUser(userDto);
            
            // 세션 갱신
            UserDto updatedUser = userService.getUserInfo(userDto.getUserId());
            updatedUser.setPassword(null);
            session.setAttribute("userInfo", updatedUser);
            
            // 변경된 정보 리턴 (프론트 반영용)
            return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return exceptionHandling(e);
        }
    }
	
	/**
	 * 7. 회원 탈퇴 (DELETE)
	 * 세션을 사용하여 현재 로그인된 사용자 탈퇴 처리 후 세션 무효화
	 * @return 성공 시 200 OK
	 */
	@DeleteMapping
	public ResponseEntity<?> delete(HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");

        if (loginUser == null) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        
        String userId = loginUser.getUserId();
        log.debug("회원 탈퇴 요청 아이디: {}", userId);
        
		try {
			userService.withdrawUser(userId);
			session.invalidate(); // 탈퇴 후 세션 무효화
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
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
