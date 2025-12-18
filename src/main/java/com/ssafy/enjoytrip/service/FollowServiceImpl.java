package com.ssafy.enjoytrip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.mapper.FollowMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    @Override
    public void follow(String fromUserId, String toUserId) throws Exception {
        // 자기 자신 팔로우 방지
        if(fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("자기 자신은 팔로우할 수 없습니다.");
        }
        // 이미 팔로우 중인지 체크
        if (followMapper.isFollowing(fromUserId, toUserId) > 0) {
            throw new IllegalStateException("이미 팔로우 중입니다.");
        }
        followMapper.follow(fromUserId, toUserId);
    }

    @Override
    public void unfollow(String fromUserId, String toUserId) throws Exception {
        followMapper.unfollow(fromUserId, toUserId);
    }

    @Override
    public List<UserDto> getFollowingList(String userId) throws Exception {
        return followMapper.selectFollowingList(userId);
    }

    @Override
    public List<UserDto> getFollowerList(String userId) throws Exception {
        return followMapper.selectFollowerList(userId);
    }

    @Override
    public Map<String, Integer> getFollowCounts(String userId) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("followingCnt", followMapper.countFollowing(userId));
        map.put("followerCnt", followMapper.countFollower(userId));
        return map;
    }

    @Override
    public boolean checkFollowing(String fromUserId, String toUserId) throws Exception {
        return followMapper.isFollowing(fromUserId, toUserId) > 0;
    }
}