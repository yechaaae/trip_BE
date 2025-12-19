package com.ssafy.enjoytrip.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.dto.BoardDto;


public interface BoardService {
    // 1. 글 작성
    void writeArticle(BoardDto boardDto) throws Exception;

    // 2. 글 목록 조회
    List<BoardDto> listArticle(Map<String, Object> map) throws Exception;

    // 3. 글 상세 조회 (조회수 증가 포함)
    BoardDto getArticle(int boardId, String userId, boolean updateHit) throws Exception;
    
    
    void modifyArticle(BoardDto boardDto) throws Exception;
    
    void deleteArticle(int boardId) throws Exception;
    
    Map<String, Object> getReviewStats(int contentId) throws Exception;
    
    void toggleLike(int boardId, String userId) throws Exception;
    
    List<BoardDto> listLikedArticle(String userId) throws Exception;
}