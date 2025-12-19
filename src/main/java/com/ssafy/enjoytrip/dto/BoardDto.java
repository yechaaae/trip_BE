package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data

public class BoardDto {
    private int boardId;
    private String userId;
    private String title;      // 관광지명
    private String content;    // 내용
    private int rating;        // 별점
    private String originalFile;
    private String saveFile;   // 실제 저장된 경로 또는 파일명
    private int hit;
    private int likeCount;
    private String registDate;
    private int type;           // 1:자유, 2:리뷰
    private Integer contentId;  // 관광지 ID (null 가능하므로 Integer)
    
    private String nickName;       // 작성자 닉네임 (user 테이블)
    private String attractionTitle;// 관광지 이름 (attractions 테이블)
    private String attractionImg;  // 관광지 이미지 (attractions 테이블)

    private Double latitude;   // 위도
    private Double longitude;  // 경도
    
    private int commentCount;
    
 // 현재 로그인한 사용자가 좋아요를 눌렀는지 여부 (true/false 또는 1/0)
    private boolean userLiked;
}