package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.dto.UserDto;


// DB 접근 전담 계층
@Mapper
public interface UserMapper {
	// 서비스에서 요청한 sql을 단순히 실행하는 용도
	// UserService 의 인터페이스와 메서드가 비슷해 보이지만 mapper는 단순 db에 넣는 용도
	
	// 1. 회원가입
	// 성공 시 1, 실패 시 0 반환
	int joinUser(UserDto userDto) throws SQLException;
	
	// 2. 로그인(Select)
	UserDto loginUser(@Param("userId") String userId, @Param("password") String password) throws SQLException;

	// 3. 아이디 중복 체크
	int idCheck(String userId) throws SQLException;
}
