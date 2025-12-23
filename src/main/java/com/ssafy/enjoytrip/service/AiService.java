package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.AiTravelRequestDto;

public interface AiService {
    String getTravelRecommendation(AiTravelRequestDto requestDto);
}