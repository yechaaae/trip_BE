package com.ssafy.enjoytrip.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

@Mapper
public interface AiMapper {
    // DB의 attraction_info나 sido/gugun 테이블에서 랜덤하게 지역명 1개를 가져옵니다.
	Map<String, String> getRandomRegion();
}