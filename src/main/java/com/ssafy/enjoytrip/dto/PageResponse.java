package com.ssafy.enjoytrip.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> list;      // 현재 페이지 데이터
    private int currentPage;   // 현재 페이지 번호
    private int totalPages;    // 총 페이지 수
    private int totalElements; // 전체 데이터 수
    private int size;          // 페이지당 데이터 수

}
