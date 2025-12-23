package com.ssafy.enjoytrip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AiTravelRequestDto {
    private String region;    // 예: 서울, 부산
    private String theme;     // 예: 맛집 탐방, 힐링, 액티비티
    private int days;         // 여행 기간
}