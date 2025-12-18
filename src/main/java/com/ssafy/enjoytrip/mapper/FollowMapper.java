package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.dto.UserDto;

@Mapper
public interface FollowMapper {
	// 1. 팔로우 하기 (성공 1, 실패0)
	int follow(@Param("fromUserId") String fromUserId, @Param("toUserId") String toUserId) throws SQLException;
	
	// 2. 언 팔로우 하기
	int unfollow(@Param("fromUserId") String fromUserId, @Param("toUserId") String toUserId) throws SQLException;
	
	// 3. 팔로잉 목록 조회 (내가 팔로우 하는 사람들)
    List<UserDto> selectFollowingList(String fromUserId) throws SQLException;
    
    // 4. 팔로워 목록 조회 (나를 팔로우 하는 사람들)
    List<UserDto> selectFollowerList(String toUserId) throws SQLException;
    
    // 5. 팔로잉 수 카운트 (내가 몇 명 팔로우 했나?)
    int countFollowing(String fromUserId) throws SQLException;
    
    // 6. 팔로워 수 카운트 (나를 몇 명이 팔로우 했나?)
    int countFollower(String toUserId) throws SQLException;
    
    // 7. 맞팔 확인용 (A가 B를 팔로우 중인지 확인) - 버튼 상태 결정용
    int isFollowing(@Param("fromUserId") String fromUserId, @Param("toUserId") String toUserId) throws SQLException;

}
