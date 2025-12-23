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

    @Override
    public String getTravelRecommendation(AiTravelRequestDto dto) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. DB에서 실제 존재하는 랜덤 지역 정보를 쿼리합니다.
        Map<String, String> regionData = aiMapper.getRandomRegion();
        String luckySido = regionData.get("sido");
        String luckyGugun = regionData.get("gugun");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model); // solar-pro2 모델 사용
        requestBody.put("temperature", 0.8); // 다양성 확보를 위해 0.8 설정

        // 2. AI 프롬프트에 DB 지역 정보를 강제로 주입하여 편향을 제거합니다.
        requestBody.put("messages", List.of(
            Map.of("role", "system", "content", 
                "당신은 대한민국 구석구석을 잘 아는 여행 전문가입니다. " +
                "반드시 다른 설명 없이 JSON 배열 형식으로만 답변하세요. " +
                "형식: [{\"city\": \"도시명\", \"title\": \"장소명\", \"description\": \"특징\", \"addr\": \"주소\"}]"),
            Map.of("role", "user", "content", 
                String.format("[%s %s] 지역에서 [%s] 테마로 즐길 수 있는 당일치기 명소 3곳을 엄선해줘. " +
                "선정된 장소들은 서로 동선이 꼬이지 않아야 하며, 반드시 지정된 시군구 내의 장소여야 해.", 
                luckySido, luckyGugun, dto.getTheme()))
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // Upstage API는 Bearer 인증을 사용합니다.

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return "[]"; 
        }
    }
}