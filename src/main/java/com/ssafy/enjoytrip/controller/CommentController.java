package com.ssafy.enjoytrip.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.CommentDto;
import com.ssafy.enjoytrip.service.CommentService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	// 목록 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> list(@PathVariable("boardId") int boardId)  {
        log.debug("listComments - boardId:{}",boardId);
        try {
        	List<CommentDto> list = commentService.listComments(boardId);
        	return new ResponseEntity<List<CommentDto>>(list,HttpStatus.OK);
        }catch(Exception e) {
        	log.error("댓글 목록 조회 실패",e);
        	return exceptionHandling(e);
        }
    	
    	
    	 
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<?> write(@RequestBody CommentDto commentDto) {
        log.debug("writeComment - {}", commentDto);
        try {
        	// TODO: 나중에 JWT 토큰 등에서 userId를 꺼내와서 서버단에서 set 해주는 것이 안전함
            // currently assumed to be sent from front-end
        	commentService.writeComment(commentDto);
        	return new ResponseEntity<String>("Success",HttpStatus.OK);
        }catch(Exception e) {
        	log.error("댓글 작성 실패",e);
        	return exceptionHandling(e);
        }
    }
    
    
    // 댓글 수정
    @PutMapping
    public ResponseEntity<?> modify(@RequestBody CommentDto commentDto){
    	log.debug("modifyComment - {}",commentDto);
    	try {
    		commentService.modifyComment(commentDto);
    		return new  ResponseEntity<String>("Success",HttpStatus.OK);
    	}catch(Exception e) {
        	log.error("댓글 수정 실패",e);
        	return exceptionHandling(e);
        }
    }
    
    
    
    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable("commentId") int commentId, @RequestBody CommentDto commentDto) throws Exception {
        log.debug("deleteComment - commentId: {}", commentId);
        try {
        	commentService.deleteComment(commentId, commentDto.getUserId());
        	return new ResponseEntity<String>("success",HttpStatus.OK);
        }catch(Exception e) {
        	log.error("댓글 삭제 실패",e);
        	return exceptionHandling(e);
        }
        
    }
    
    
    // 예외 처리 공통 메서드
    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
