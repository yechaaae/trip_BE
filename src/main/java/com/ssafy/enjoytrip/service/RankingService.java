package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.RankingDto;
import java.util.List;

public interface RankingService {

    // 리뷰 수 랭킹
    List<RankingDto> getReviewRanking();

    // 좋아요 수 랭킹
    List<RankingDto> getLikeRanking();

    // 뱃지 수 랭킹
    List<RankingDto> getBadgeRanking();
}
