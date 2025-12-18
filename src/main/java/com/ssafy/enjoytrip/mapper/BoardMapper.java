package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.dto.BoardDto; // DTO 위치에 맞춰 수정 필요

@Mapper // Spring이 이 인터페이스를 찾아서 구현체를 자동으로 만들어줍니다.
public interface BoardMapper {

    // 1. 글 작성
    void writeArticle(BoardDto boardDto) throws SQLException;

    // 2. 글 목록 조회
    List<BoardDto> listArticle(Map<String, Object> map) throws SQLException;

    // 3. 글 상세 조회 (내용 가져오기)
    BoardDto getArticle(int boardId) throws SQLException;

    // 4. 조회수 증가 (상세 조회 시 호출)
    void updateHit(int boardId) throws SQLException;

    // 5. 글 수정
    void modifyArticle(BoardDto boardDto) throws SQLException;

    // 6. 글 삭제
    void deleteArticle(int boardId) throws SQLException;

    List<BoardDto> listMyArticle(String userId);
}
