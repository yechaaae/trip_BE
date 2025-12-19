package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.dto.RankingDto;
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
	
	// 4. 회원 정보 조회
	UserDto selectUserByUserId(String userId) throws SQLException;
	
	// 5. 회원 정보 수정
	int updateUser(UserDto userDto) throws SQLException; // nickname, email, birthe 등 정보 수정
	
	// 6. 회원 탈퇴
	int deleteUser(String userId) throws SQLException;
	// 7. 이메일 체크
	int emailCheck(String email) throws SQLException;
	
	// 닉네임 중복 체크 (중복이면 1, 아니면 0 반환)
    int countByNickname(String nickname);

    // 전화번호 중복 체크
    int countByPhoneNumber(String phoneNumber);
    
    // 리뷰 수 랭킹
    List<RankingDto> selectReviewRanking();

    // 좋아요 수 랭킹
    List<RankingDto> selectLikeRanking();

    // 뱃지 수 랭킹
    List<RankingDto> selectBadgeRanking();
    
}
