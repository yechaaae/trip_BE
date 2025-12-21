package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class RankingDto {

    private String nickName;
    private int value; // 리뷰 수, 좋아요 수, 뱃지 수
    private String userId;
    private String profileImg;
    private String introduction;
    private int reviews; // 프로필 방문 팝업에 보일 리뷰 수

}
