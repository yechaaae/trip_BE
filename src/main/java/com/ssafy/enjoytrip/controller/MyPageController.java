package com.ssafy.enjoytrip.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.BookmarkDto;
import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.mapper.BoardMapper; // Service를 거치는 게 정석이지만 약식 구현
import com.ssafy.enjoytrip.mapper.BookmarkMapper;

@RestController
@RequestMapping("/api/mypage")
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MyPageController {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BookmarkMapper bookmarkMapper;

    // 1. 내 리뷰 목록 가져오기
    @GetMapping("/review")
    public ResponseEntity<?> getMyReviews(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("userInfo");
        if (user == null) return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);

        try {
            List<BoardDto> list = boardMapper.listMyArticle(user.getUserId());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 저장한 관광지 목록 가져오기
    @GetMapping("/bookmark")
    public ResponseEntity<?> getMyBookmarks(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("userInfo");
        if (user == null) return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);

        try {
            List<BookmarkDto> list = bookmarkMapper.listMyBookmark(user.getUserId());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. 관광지 찜하기 (관광지 검색 모달 등에서 사용)
    @PostMapping("/bookmark")
    public ResponseEntity<?> addBookmark(@RequestBody BookmarkDto bookmarkDto, HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("userInfo");
        if (user == null) return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);

        try {
            bookmarkDto.setUserId(user.getUserId());
            bookmarkMapper.addBookmark(bookmarkDto);
            return new ResponseEntity<>("저장 완료", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}