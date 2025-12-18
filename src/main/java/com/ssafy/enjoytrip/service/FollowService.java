package com.ssafy.enjoytrip.service;

import java.util.List;
import java.util.Map;
import com.ssafy.enjoytrip.dto.UserDto;

public interface FollowService {
    void follow(String fromUserId, String toUserId) throws Exception;
    void unfollow(String fromUserId, String toUserId) throws Exception;
    List<UserDto> getFollowingList(String userId) throws Exception;
    List<UserDto> getFollowerList(String userId) throws Exception;
    
    // 카운트 정보를 Map으로 한 번에 묶어서 리턴 
    Map<String, Integer> getFollowCounts(String userId) throws Exception;
    
    // 특정 유저를 팔로우 중인지 확인
    boolean checkFollowing(String fromUserId, String toUserId) throws Exception;
}