                       package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class AttractionDto {
    private int contentId;         // 관광지 고유 ID (상세 조회 링크용)
    private int contentTypeId;     // 타입 (12:관광지, 14:문화시설, 32:숙박, 39:음식점)
    private String title;          // 관광지명 (마커 라벨)
    private String addr1;          // 주소
    private String firstImage;     // 썸네일 이미지
    private String firstImage2;    // 썸네일 이미지(저해상도) - 필요시
    private int sidoCode;          // 시도 코드
    private int gugunCode;         // 구군 코드
    private double latitude;       // 위도 (mapy) - ★ 지도 필수
    private double longitude;      // 경도 (mapx) - ★ 지도 필수
    private String tel;            // 전화번호 (목록에서도 종종 쓰임)
}