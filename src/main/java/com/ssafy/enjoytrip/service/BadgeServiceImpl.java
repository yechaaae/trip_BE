package com.ssafy.enjoytrip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.dto.AttractionDto; // 관광지 DTO 필요
import com.ssafy.enjoytrip.dto.BadgeDto;
import com.ssafy.enjoytrip.mapper.AttractionMapper; // 관광지 정보 조회용
import com.ssafy.enjoytrip.mapper.BadgeMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    private BadgeMapper badgeMapper;

    @Autowired
    private AttractionMapper attractionMapper; // 관광지 정보를 가져오기 위해 필요

    @Override
    @Transactional
    public boolean checkAndGiveBadge(String userId, int contentId) {
        boolean isNewBadgeEarned = false; // 신규 획득 여부 플래그
        
        AttractionDto attr = attractionMapper.getAttraction(contentId); 
        if (attr == null) return false;

        int sidoCode = attr.getSidoCode();
        int gugunCode = attr.getGugunCode();

        Map<String, Integer> regionParams = new HashMap<>();
        regionParams.put("sidoCode", sidoCode);
        regionParams.put("gugunCode", gugunCode);

        BadgeDto siBadge = badgeMapper.findBadgeByRegion(regionParams);

        if (siBadge != null) {
            Map<String, Object> checkParams = new HashMap<>();
            checkParams.put("userId", userId);
            checkParams.put("badgeId", siBadge.getBadgeId());

            if (badgeMapper.hasBadge(checkParams) == 0) {
                badgeMapper.giveBadge(checkParams);
                isNewBadgeEarned = true; // 뱃지 지급됨
                
                // 도 뱃지 체크 로직 실행 (도 뱃지도 새로 받으면 true 유지)
                if (checkDoBadge(userId, sidoCode)) {
                    isNewBadgeEarned = true;
                }
            }
        }
        return isNewBadgeEarned;
    }

    // [2단계] 도(Do) 뱃지 획득 로직 (내부 호출용)
    private boolean checkDoBadge(String userId, int sidoCode) {
        // 1. 해당 도의 전체 구군 개수 조회
        int totalGuguns = badgeMapper.countTotalGuguns(sidoCode);

        // 2. 유저가 현재까지 모은 해당 도의 구군 뱃지 개수 조회
        Map<String, Object> countParams = new HashMap<>();
        countParams.put("userId", userId);
        countParams.put("sidoCode", sidoCode);
        int userEarnedGuguns = badgeMapper.countUserEarnedGuguns(countParams);

        log.info("🏆 [Do Badge Check] Sido: {}, UserEarned: {} / Total: {}", sidoCode, userEarnedGuguns, totalGuguns);

        // 3. 모든 구군을 다 모았는지 확인
        if (totalGuguns > 0 && totalGuguns <= userEarnedGuguns) {
            // 도 뱃지 정보 찾기 (gugunCode가 0인 것이 도 뱃지 규칙)
            Map<String, Integer> doParams = new HashMap<>();
            doParams.put("sidoCode", sidoCode);
            doParams.put("gugunCode", 0);

            BadgeDto doBadge = badgeMapper.findBadgeByRegion(doParams);

            if (doBadge != null) {
                // 4. 이미 가지고 있는지 중복 확인
                Map<String, Object> checkParams = new HashMap<>();
                checkParams.put("userId", userId);
                checkParams.put("badgeId", doBadge.getBadgeId());

                if (badgeMapper.hasBadge(checkParams) == 0) {
                    // 5. 중복이 아닐 때만 실제 지급하고 true 반환
                    badgeMapper.giveBadge(checkParams);
                    log.info("👑 [Master Badge Given] User: {}, Badge: {}", userId, doBadge.getName());
                    return true; // 새로 획득함
                }
            }
        }
        return false; // 이미 있었거나 조건을 만족하지 못함
    }

    @Override
    public List<BadgeDto> getMyBadges(String userId) {
        return badgeMapper.listMyBadges(userId);
    }
}