package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.RankingDto;
import com.ssafy.enjoytrip.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    private final UserMapper userMapper;

    @Autowired
    public RankingServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<RankingDto> getReviewRanking() {
        // 리뷰 수 기준 랭킹 조회
        return userMapper.selectReviewRanking();
    }

    @Override
    public List<RankingDto> getLikeRanking() {
        // 좋아요 수 기준 랭킹 조회
        return userMapper.selectLikeRanking();
    }

    @Override
    public List<RankingDto> getBadgeRanking() {
        // 뱃지 수 기준 랭킹 조회
        return userMapper.selectBadgeRanking();
    }
}
