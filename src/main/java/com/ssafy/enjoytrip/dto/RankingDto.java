package com.ssafy.enjoytrip.dto;

public class RankingDto {

    private String nickname;
    private int value; // 리뷰 수, 좋아요 수, 뱃지 수

    // Getters and Setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
