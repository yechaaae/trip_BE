package com.ssafy.enjoytrip.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	// Mapper 객체 주입(DB 담당) 
	private final UserMapper userMapper;

	@Override
	@Transactional // 회원 가입 도중 에러나면 자동 롤백 처리
	public int joinUser(UserDto userDto) throws Exception {
		// TODO : 비밀번호 암호화 로직등 추가 가능
		return userMapper.joinUser(userDto);
	}

	@Override
	public UserDto loginUser(String userId, String password) throws Exception {
		// 로그인 로직: Mapper에게 아이디/ 비번 주고 일치하는 사람 있는지 물어봄
		return userMapper.loginUser(userId, password);
	}

	@Override
	public int idCheck(String userId) throws Exception {
		// 0이면 사용 가능, 1이면 중복
		return userMapper.idCheck(userId);
	}

}
