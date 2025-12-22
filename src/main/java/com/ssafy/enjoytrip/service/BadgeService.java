package com.ssafy.enjoytrip.service;

import java.util.List;
import com.ssafy.enjoytrip.dto.BadgeDto;

public interface BadgeService {
    // 리뷰 작성 시 호출될 메서드
    boolean checkAndGiveBadge(String userId, int contentId);
    
    // 내 뱃지 함 조회
    List<BadgeDto> getMyBadges(String userId);
}