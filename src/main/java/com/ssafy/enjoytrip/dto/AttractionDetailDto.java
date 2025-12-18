package com.ssafy.enjoytrip.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // 부모 필드도 포함해서 비교
public class AttractionDetailDto extends AttractionDto {
    private String overview;       // 상세 설명 (★ 핵심)
    private String homepage;       // 홈페이지 링크
    private String cat1;           // 대분류
    private String cat2;           // 중분류
    private String cat3;           // 소분류
    private String zipcode;        // 우편번호
}