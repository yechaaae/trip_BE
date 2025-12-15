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
   
}