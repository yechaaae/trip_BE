package com.ssafy.enjoytrip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.service.FollowService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FollowController {

    private final FollowService followService;

    // 1. 팔로우 하기 (POST /follow/{toUserId})
    @PostMapping("/{toUserId}")
    public ResponseEntity<?> follow(@PathVariable("toUserId") String toUserId, HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");
        if (loginUser == null) return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        
        try {
            followService.follow(loginUser.getUserId(), toUserId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IllegalStateException e) {
             return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT); // 이미 팔로우 중
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 언팔로우 하기 (DELETE /follow/{toUserId})
    @DeleteMapping("/{toUserId}")
    public ResponseEntity<?> unfollow(@PathVariable("toUserId") String toUserId, HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");
        if (loginUser == null) return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

        try {
            followService.unfollow(loginUser.getUserId(), toUserId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. 목록 조회 (팔로잉/팔로워)
    // type: "following" or "follower"
    @GetMapping("/list/{type}/{userId}")
    public ResponseEntity<?> getFollowList(@PathVariable("type") String type, @PathVariable("userId") String userId) {
        try {
            List<UserDto> list = null;
            if ("following".equals(type)) {
                list = followService.getFollowingList(userId);
            } else if ("follower".equals(type)) {
                list = followService.getFollowerList(userId);
            }
            return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. 카운트 조회 (팔로워 수 + 팔로잉 수)
    // GET /follow/count/{userId}
    @GetMapping("/count/{userId}")
    public ResponseEntity<?> getFollowCounts(@PathVariable("userId") String userId) {
        try {
            Map<String, Integer> counts = followService.getFollowCounts(userId);
            return new ResponseEntity<Map<String, Integer>>(counts, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 5. 팔로우 여부 확인 (버튼 색깔 결정용)
    // GET /follow/status/{toUserId}
    @GetMapping("/status/{toUserId}")
    public ResponseEntity<?> checkFollowStatus(@PathVariable("toUserId") String toUserId, HttpSession session){
        UserDto loginUser = (UserDto) session.getAttribute("userInfo");
        if (loginUser == null) return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        
        try {
            boolean isFollowing = followService.checkFollowing(loginUser.getUserId(), toUserId);
            return new ResponseEntity<Boolean>(isFollowing, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}