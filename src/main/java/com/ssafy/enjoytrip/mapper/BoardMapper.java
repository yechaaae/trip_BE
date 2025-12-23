package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.dto.BoardDto; // DTO 위치에 맞춰 수정 필요

@Mapper // Spring이 이 인터페이스를 찾아서 구현체를 자동으로 만들어줍니다.
public interface BoardMapper {

    // 1. 글 작성
    void writeArticle(BoardDto boardDto) throws SQLException;

    // 2. 글 목록 조회
    List<BoardDto> listArticle(Map<String, Object> map) throws SQLException;

    // 3. 글 상세 조회 (내용 가져오기)
    BoardDto getArticle(Map<String, Object> map) throws SQLException;

    // 4. 조회수 증가 (상세 조회 시 호출)
    void updateHit(int boardId) throws SQLException;

    // 5. 글 수정
    void modifyArticle(BoardDto boardDto) throws SQLException;

    // 6. 글 삭제
    void deleteArticle(int boardId) throws SQLException;
    
    //리뷰 별점 
    Map<String, Object> getReviewStats(@Param("contentId") int contentId);


    List<BoardDto> listMyArticle(String userId);
    
 // 9. 좋아요 여부 확인 (1: 누름, 0: 안누름)
    int checkLike(Map<String, Object> map) throws SQLException;

    // 10. 좋아요 추가
    void addLike(Map<String, Object> map) throws SQLException;

    // 11. 좋아요 취소
    void deleteLike(Map<String, Object> map) throws SQLException;

    // 12. board 테이블의 좋아요 수(like_count) 동기화
    void updateLikeCount(int boardId) throws SQLException;
    
    List<BoardDto> listLikedArticle(String userId) throws SQLException;
    
 // 관광지 리뷰 개수
    int countPlaceReview(@Param("contentId") int contentId);

    List<BoardDto> selectPlaceReviews(
    	    @Param("contentId") int contentId,
    	    @Param("offset") int offset,
    	    @Param("size") int size,
    	    @Param("sort") String sort
    	);



}
