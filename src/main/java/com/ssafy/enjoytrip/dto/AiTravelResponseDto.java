package com.ssafy.enjoytrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiTravelResponseDto {
    private String recommendation; // AI가 생성한 여행 코스 텍스트
}