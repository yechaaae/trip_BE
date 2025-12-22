package com.ssafy.enjoytrip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.dto.BadgeDto;

@Mapper
public interface BadgeMapper {
	// 1. 특정 지역(sido, gugun)에 해당하는 뱃지 정보 찾기
    BadgeDto findBadgeByRegion(Map<String, Integer> params);

    // 2. 유저가 해당 뱃지를 이미 가지고 있는지 확인 (1이면 보유, 0이면 미보유)
    int hasBadge(Map<String, Object> params);

    // 3. 뱃지 지급 (Insert)
    void giveBadge(Map<String, Object> params);

    // 4. [도 뱃지용] 해당 도(Sido)의 전체 구군 개수 조회
    int countTotalGuguns(int sidoCode);

    // 5. [도 뱃지용] 유저가 획득한 해당 도(Sido)의 시군구 뱃지 개수 조회
    int countUserEarnedGuguns(Map<String, Object> params);

    // 6. 내 뱃지 목록 조회 (마이페이지용)
    List<BadgeDto> listMyBadges(String userId);
	
}
