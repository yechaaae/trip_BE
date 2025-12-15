package com.ssafy.enjoytrip.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.service.BoardService;

// import io.swagger.v3... (이건 지워야 합니다!)

@RestController
@RequestMapping("/api/board")
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
        
        // 🔹 try 문을 여기서 시작해야 합니다.
        try {
            // 1. 세션 체크
            UserDto userDto = (UserDto) session.getAttribute("userInfo");
            if (userDto == null) {
                return new ResponseEntity<String>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
            }

            // 2. ID 세팅
            boardDto.setUserId(userDto.getUserId());

            // 3. 파일 처리 (일단 유지)
            if (file != null && !file.isEmpty()) {
                String saveFolder = "C:/ssafy/upload/";
                String originalFileName = file.getOriginalFilename();
                String saveFileName = UUID.randomUUID() + "_" + originalFileName;
                
                // 폴더가 없으면 에러나므로 안전장치 하나만 추가함
                File folder = new File(saveFolder);
                if (!folder.exists()) folder.mkdirs();

                file.transferTo(new File(saveFolder + saveFileName));
                
                boardDto.setOriginalFile(originalFileName);
                boardDto.setSaveFile(saveFileName);
            }

            boardService.writeArticle(boardDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 찍어보기
            return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 목록 조회
    @GetMapping
    public ResponseEntity<List<BoardDto>> list() throws Exception {
        return new ResponseEntity<>(boardService.listArticle(), HttpStatus.OK);
    }

    // 3. 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> detail(@PathVariable int boardId) throws Exception {
        return new ResponseEntity<>(boardService.getArticle(boardId), HttpStatus.OK);
    }
    
 // 4. 글 수정
    @PutMapping
    public ResponseEntity<?> modify(@RequestBody BoardDto boardDto, HttpSession session) throws Exception {
        // 1. 로그인 체크
        UserDto member = (UserDto) session.getAttribute("userInfo");
        if (member == null) return new ResponseEntity<>("로그인 필요", HttpStatus.UNAUTHORIZED);

        // 2. 작성자 본인 확인 로직 
        // 수정하려는 글의 정보를 DB에서 먼저 가져옴
        BoardDto originalBoard = boardService.getArticle(boardDto.getBoardId());
        
        // 글이 없거나, 작성자가 다르면 거부
        if (originalBoard == null || !member.getUserId().equals(originalBoard.getUserId())) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        // 3. 본인이 맞으면 수정 진행
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
        BoardDto originalBoard = boardService.getArticle(boardId);
        
        if (originalBoard == null || !member.getUserId().equals(originalBoard.getUserId())) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        // 3. 본인이 맞으면 삭제 진행
        boardService.deleteArticle(boardId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
}