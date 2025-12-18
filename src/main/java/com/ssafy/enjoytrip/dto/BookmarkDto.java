package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class BookmarkDto {
    private int bookmarkId;
    private String userId;
    private int contentId;
    private String title;
    private String addr1;
    private String firstImage;
    private Double latitude;   // 위도
    private Double longitude;  // 경도
}