package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.AiTravelRequestDto;
import com.ssafy.enjoytrip.dto.AiTravelResponseDto;
import com.ssafy.enjoytrip.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AiController {

    private final AiService aiService;

    @PostMapping("/recommend")
    public ResponseEntity<AiTravelResponseDto> recommendTravel(@RequestBody AiTravelRequestDto requestDto) {
        String result = aiService.getTravelRecommendation(requestDto);
        return ResponseEntity.ok(new AiTravelResponseDto(result));
    }
}