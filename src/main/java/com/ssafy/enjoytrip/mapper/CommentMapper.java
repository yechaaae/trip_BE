package com.ssafy.enjoytrip.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.dto.CommentDto;

@Mapper
public interface CommentMapper {
	
	// 1. 댓글 작성
	int writeComment(CommentDto commentDto)throws SQLException;
	
	// 2. 특정 게시글의 모든 댓글 조회(계층 상관없이 평탄하게 가져옴)
	List<CommentDto> listComments(int boardId)throws SQLException;
	
	// 3. 댓글 삭제 (DB 삭제가 아니라 is_deleted 상태만 변경)
	int deleteComment(@Param("commentId")int commentId, @Param("userId") String userId)throws SQLException;
	
	// 4. 댓글 수정
	int modifyComment(CommentDto commentDto)throws SQLException;

    // 5. 관리자로 댓글 삭제
    int deleteCommentByAdmin(int commentId) throws SQLException;
}
