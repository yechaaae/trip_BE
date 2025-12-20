package com.ssafy.enjoytrip.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; // 🔥 중요: Spring용 RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.PageResponse;
import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.service.BoardService;



@RestController
@RequestMapping("/api/board")
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 1. 글 작성
    @PostMapping
    public ResponseEntity<?> write(
            @RequestPart("boardDto") BoardDto boardDto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpSession session) {

        try {
            // 1. 로그인 체크
            UserDto userDto = (UserDto) session.getAttribute("userInfo");
            if (userDto == null) {
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
            }

            boardDto.setUserId(userDto.getUserId());

            // 🔥 2. 게시글 타입별 처리
            if (boardDto.getType() == 2) { // 리뷰
                if (boardDto.getContentId() == null) {
                    return new ResponseEntity<>("리뷰는 관광지를 반드시 선택해야 합니다.", HttpStatus.BAD_REQUEST);
                }
               
            } else {
                // 자유게시판
                boardDto.setContentId(null);
            }

            // 🔍 디버그
            System.out.println("type = " + boardDto.getType());
            System.out.println("contentId = " + boardDto.getContentId());

            // 3. 파일 처리
            if (file != null && !file.isEmpty()) {
                String saveFolder = "C:/ssafy/upload/";
                String originalFileName = file.getOriginalFilename();
                String saveFileName = UUID.randomUUID() + "_" + originalFileName;

                File folder = new File(saveFolder);
                if (!folder.exists()) folder.mkdirs();

                file.transferTo(new File(saveFolder + saveFileName));

                boardDto.setOriginalFile(originalFileName);
                boardDto.setSaveFile(saveFileName);
            }

            boardService.writeArticle(boardDto);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 2. 목록 조회
    @GetMapping
    public ResponseEntity<List<BoardDto>> list(
            @RequestParam(value = "type", required = false, defaultValue = "0") int type,
            @RequestParam(value = "word", required = false) String word,
            HttpSession session
    ) throws Exception {
        
        // 검색 조건과 게시판 타입을 Map에 담아서 서비스로 전달
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("word", word);
        // 로그인한 유저라면 userId를 맵에 담아 보냄 (좋아요 체크용)
        UserDto userDto = (UserDto) session.getAttribute("userInfo");
        if (userDto != null) {
            map.put("userId", userDto.getUserId());
        }
        
        return new ResponseEntity<>(boardService.listArticle(map), HttpStatus.OK);
    }
    // 3. 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> detail(@PathVariable int boardId,
    		@RequestParam(value = "updateHit", required = false, defaultValue = "true") boolean updateHit,
    		HttpSession session) throws Exception {
    	String userId = null;
        UserDto userDto = (UserDto) session.getAttribute("userInfo");
        if (userDto != null) {
            userId = userDto.getUserId();
        }
    	
    	return new ResponseEntity<>(boardService.getArticle(boardId,userId,updateHit), HttpStatus.OK);
    }
    
 // 4. 글 수정
    @PostMapping("/modify") 
    public ResponseEntity<?> modify(
            @RequestPart("boardDto") BoardDto boardDto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpSession session) throws Exception {
        
        // 1. 로그인 체크
        UserDto member = (UserDto) session.getAttribute("userInfo");
        if (member == null) return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);

        // 2. 작성자 본인 확인
        // false: 조회수 증가 방지
        BoardDto originalBoard = boardService.getArticle(boardDto.getBoardId(), member.getUserId(), false);
        
        if (originalBoard == null || !member.getUserId().equals(originalBoard.getUserId())) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        // 3. 파일 처리 (새로운 파일이 올라왔을 때만)
        if (file != null && !file.isEmpty()) {
            String saveFolder = "C:/ssafy/upload/";
            String originalFileName = file.getOriginalFilename();
            String saveFileName = UUID.randomUUID() + "_" + originalFileName;

            File folder = new File(saveFolder);
            if (!folder.exists()) folder.mkdirs();

            file.transferTo(new File(saveFolder + saveFileName));

            // DTO에 새 파일 정보 세팅
            boardDto.setOriginalFile(originalFileName);
            boardDto.setSaveFile(saveFileName);
        } else {
            // 새 파일이 없으면 기존 파일 정보 유지 (혹은 DB 쿼리에서 null 체크로 처리)
            // Mapper XML에 <if> 처리가 되어 있으므로 null로 넘겨도 무관
        }

        // 4. 수정 진행
        boardService.modifyArticle(boardDto);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 5. 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@PathVariable int boardId, HttpSession session) throws Exception {
        // 1. 로그인 체크
        UserDto member = (UserDto) session.getAttribute("userInfo");
        if (member == null) return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);

        // 2. 작성자 본인 확인 로직 
        BoardDto originalBoard = boardService.getArticle(boardId,member.getUserId(),false);
        
        if (originalBoard == null || !member.getUserId().equals(originalBoard.getUserId())) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        // 3. 본인이 맞으면 삭제 진행
        boardService.deleteArticle(boardId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    
    // 6. 관광지별 리뷰 통계 (리뷰 수 + 평균 별점)
    @GetMapping("/review/stats/{contentId}")
    public ResponseEntity<Map<String, Object>> getReviewStats(
            @PathVariable int contentId) throws Exception {

        Map<String, Object> stats = boardService.getReviewStats(contentId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    @PostMapping("/like/{boardId}")
    public ResponseEntity<?> toggleLike(@PathVariable int boardId, @RequestBody Map<String, String> map) {
        
        String userId = map.get("userId");
        
        try {
            boardService.toggleLike(boardId, userId);
            return new ResponseEntity<String>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    
    @GetMapping("/place-reviews")
    public PageResponse<BoardDto> getPlaceReviews(
            @RequestParam int contentId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "latest") String sort
    ) {
        return boardService.getPlaceReviews(contentId, page, size, sort);
    }

    
    
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
    
}