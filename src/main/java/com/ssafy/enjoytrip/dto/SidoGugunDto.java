package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class SidoGugunDto {
    private int code;       // 지역 코드 (예: 서울 1, 강남구 1)
    private String name;    // 지역 이름 (예: 서울, 강남구)
}