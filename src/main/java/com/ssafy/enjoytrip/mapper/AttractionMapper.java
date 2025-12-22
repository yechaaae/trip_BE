package com.ssafy.enjoytrip.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.enjoytrip.dto.AttractionDto;

@Mapper
public interface AttractionMapper {
    // 뱃지 서비스에서 호출하는 메서드
    AttractionDto getAttraction(int contentId);
}