package com.ssafy.enjoytrip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.enjoytrip.dto.AttractionDto;

@Mapper
public interface AttractionMapper {
	// 1. 특정 관광지 단건 조회
    AttractionDto getAttraction(int contentId);

    // 2. 시도 코드에 따른 구군 목록 조회
    List<Map<String, Object>> listGugun(int sidoCode);

    // 3. 필터 및 위치 기반 관광지 목록 검색
    List<AttractionDto> searchLocalAttractions(Map<String, Object> params);

    // 4. 검색 조건에 맞는 전체 데이터 개수 조회 (페이징용)
    int getLocalSearchCount(Map<String, Object> params);
}