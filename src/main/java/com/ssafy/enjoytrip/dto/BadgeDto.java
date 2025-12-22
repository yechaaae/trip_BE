package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class BadgeDto {
	// badges 테이블 컬럼
    private int badgeId;
    private String name;
    private String image;
    private int sidoCode;
    private int gugunCode;
    private String type; // 'SI' or 'DO'

    // user_badges 테이블 컬럼 (획득 날짜)
    private String getDate;
	

}
