package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class AttractionDto {
    private int no;             // PK
    private int contentId;      // content_id
    private String title;       // title
    private int contentTypeId;  // content_type_id
    
    // ⭐ 뱃지 로직과 연결하기 위해 변수명은 sidoCode, gugunCode 유지
    private int sidoCode;       // DB: area_code 와 매핑됨
    private int gugunCode;      // DB: si_gun_gu_code 와 매핑됨
    
    private String firstImage;  // DB: first_image1
    private String firstImage2; // DB: first_image2
    
    private double latitude;    // latitude
    private double longitude;   // longitude
    
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
}