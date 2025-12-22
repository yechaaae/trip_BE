package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.service.BoardService;
import com.ssafy.enjoytrip.service.CommentService;
import com.ssafy.enjoytrip.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminRestController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService; // 추가 주입

    // 1. 유저 목록 조회
    @GetMapping("/user")
    public ResponseEntity<?> userList() {
        try {
            return new ResponseEntity<>(userService.listUser(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 게시글 강제 삭제
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable int boardId) {
        try {
            boardService.deleteArticle(boardId); // BoardMapper의 deleteArticle 호출
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. 댓글 강제 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        try {
            commentService.deleteCommentByAdmin(commentId); // 새로 만든 강제 삭제 호출
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 강제 탈퇴
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> kickUser(@PathVariable("userId") String userId) {
        try {
            // 기존 UserService의 탈퇴 로직 재사용
            userService.withdrawUser(userId);
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}