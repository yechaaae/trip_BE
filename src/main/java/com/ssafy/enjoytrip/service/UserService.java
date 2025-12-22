package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.UserDto;

import java.util.List;

public interface UserService {
		// 서비스는 UserMapper에 있는 여러가지 메서드를 조합해서 하나의 (예: 회원가입) 로직을 처리
	
		// 1. 회원가입
		int joinUser(UserDto userDto) throws Exception;

		// 2. 로그인
		UserDto loginUser(String userId, String password) throws Exception;
		
		// 3. 아이디 중복 체크
		int idCheck(String userId) throws Exception;
		
		// 4. 회원 정보 조회
	    UserDto getUserInfo(String userId) throws Exception;
		// 5. 회원 정보 수정
	    int modifyUser(UserDto userDto) throws Exception;
		
		// 6. 회원 탈퇴
		int withdrawUser(String userId) throws Exception;
		
		//7.이메일 중복 체크
		int emailCheck(String email) throws Exception;
		
		// 8. 닉네임 중복 체크
	    int nicknameCheck(String nickname) throws Exception;
	    
	    // 9. 전화번호 중복 체크
	    int phoneNumberCheck(String phoneNumber) throws Exception;

        // 10. 관리자 기능 처리 로직 추가
        List<UserDto> listUser() throws Exception;
}
