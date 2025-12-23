package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.dto.AiTravelRequestDto;
import com.ssafy.enjoytrip.mapper.AiMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiMapper aiMapper; // 매퍼 주입

    @Value("${upstage.api.key}")
    private String apiKey;

    @Value("${upstage.api.url}")
    private String apiUrl;

    @Value("${upstage.model}")
    private String model;

    // AiServiceImpl.java
    @Override
    public String getTravelRecommendation(AiTravelRequestDto dto) {
        // RestTemplate이 선언되지 않았다면 여기서 생성합니다.
        RestTemplate restTemplate = new RestTemplate();

        // 1. 선택된 카테고리 ID로 DB 조회
        Map<String, Object> luckyPlace = aiMapper.getRandomAttraction(dto.getContentTypeId());

        String title = (String) luckyPlace.get("title");
        String contentId = String.valueOf(luckyPlace.get("contentId"));
        String addr = (String) luckyPlace.get("addr1");
        String image = (String) luckyPlace.get("image");

        // 카테고리별 맞춤 단어 설정
        String categoryName = switch(dto.getContentTypeId()) {
            case "15" -> "축제";
            case "32" -> "숙소";
            case "39" -> "음식점";
            default -> "관광지";
        };

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "당신은 전문 여행 가이드입니다. 반드시 순수 JSON으로만 응답하세요."),
                Map.of("role", "user", "content",
                        String.format("'%s'라는 이름의 [%s]를 무작위로 선정했어. 이 곳의 매력을 설명하는 아주 짧고 매력적인 멘트 한 문장을 써줘. " +
                                        "형식: {\"id\": \"%s\", \"title\": \"%s\", \"description\": \"추천멘트\", \"addr\": \"%s\", \"image\": \"%s\"}",
                                title, categoryName, contentId, title, addr, image))
        ));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 기존 코드의 호출 방식 유지
            Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return "{}";
        }
    }
}