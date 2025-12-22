package com.ssafy.enjoytrip.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.BadgeDto;
import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.service.BadgeService;

@RestController
@RequestMapping("/api/badge")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    // 내 뱃지 목록 조회
    @GetMapping("/my")
    public ResponseEntity<?> getMyBadges(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("userInfo");
        if (user == null) {
            return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);
        }

        List<BadgeDto> list = badgeService.getMyBadges(user.getUserId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
 // 타인의 뱃지 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBadges(@PathVariable("userId") String userId) {
        // 이미 만들어둔 Service가 userId를 파라미터로 받으므로 재사용 가능!
        List<BadgeDto> list = badgeService.getMyBadges(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}