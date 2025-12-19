package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.dto.CommentDto;

public interface CommentService {
	
	// 1.댓글 작성
	void writeComment(CommentDto commentDto) throws Exception;
	
	// 2.댓글 삭제
	void deleteComment(int commentId, String userId) throws Exception;
	
	// 3.댓글 수정
	void modifyComment(CommentDto commentDto) throws Exception;
	
	//4.댓글 조회
	List<CommentDto> listComments(int boardId) throws Exception;

}
