package com.ssafy.enjoytrip.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssafy.enjoytrip.dto.AttractionDto;
import com.ssafy.enjoytrip.dto.PageResponse;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionServiceImpl implements AttractionService {
	
	
	
	@Value("${TOUR_CATEGORY_CODE_URL}")
	private String tourCategoryCodeUrl;
	
	@Value("${TOUR_AREA_BASED_LIST2_URL}")
	private String tourAreaBasedList2Url;
	
	@Value("${TOUR_LOCATION_BASED_LIST_URL}")
	private String tourLocationBasedListUrl;
	
	@Value("${TOUR_SEARCH_KEYWORD_URL}")
	private String tourSearchKeywordUrl;
	
	@Value("${TOUR_FESTIVAL_INFO_URL}")
	private String tourFestivalInfoUrl;
	
	@Value("${TOUR_PET_TOUR_URL}")
	private String tourPetTourUrl;
	
	@Value("${TOUR_STAY_URL}")
	private String tourStayUrl;

	
	
    @Value("${TOUR_API_KEY}")
    private String serviceKey;

    @Value("${TOUR_AREA_BASED_LIST_URL}")
    private String tourAreaBasedListUrl;

    @Value("${TOUR_DETAIL_COMMON_URL}")
    private String tourDetailCommonUrl;

    @Value("${TOUR_AREA_CODE_URL}")
    private String tourAreaCodeUrl;

    @Value("${TOUR_DETAIL_INTRO_URL}")
    private String tourDetailIntroUrl;

    @Value("${TOUR_DETAIL_INFO_URL}")
    private String tourDetailInfoUrl;

    @Value("${TOUR_DETAIL_IMAGE_URL}")
    private String tourDetailImageUrl;

    @Value("${TOUR_DIclsSystm_CODE_URL}")
    private String tourDIclsSystmCodeUrl;

    @Value("${TOUR_AREA_BASED_SYNC_LIST_URL}")
    private String tourAreaBasedSyncListUrl;

    @Value("${TOUR_IDONG_CODE_URL}")
    private String tourIdongCodeUrl;

    // 1. 지역 기반 관광지 목록 조회
    @Override
    public String getAreaBasedList(
            String areaCode,
            String contentTypeId,
            int pageNo,
            int numOfRows
    ) throws Exception {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(tourAreaBasedListUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
                .queryParam("arrange", "A")
                .queryParam("pageNo", pageNo)          
                .queryParam("numOfRows", numOfRows); 

        if (areaCode != null && !areaCode.isBlank()) {
            builder.queryParam("areaCode", areaCode);
        }

        if (contentTypeId != null && !contentTypeId.isBlank()) {
            builder.queryParam("contentTypeId", contentTypeId);
        }

        URI uri = builder.build(true).toUri(); // encode 포함
        log.debug("TourAPI [areaBasedList] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 2. 지역 코드 조회
    @Override
    public String getAreaCode(String areaCode) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourAreaCodeUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
                .queryParam("numOfRows", "100");

        if (areaCode != null && !areaCode.isEmpty()) {
            builder.queryParam("areaCode", areaCode);
        }

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [areaCode] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 3. 공통 상세 정보 조회
    @Override
    public String getDetailCommon(String contentId) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourDetailCommonUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
                .queryParam("contentId", contentId);
                // ★ 중요: 아래 YN 파라미터가 없으면 좌표와 설명이 안 옵니다!
                

        URI uri = builder.build(true).toUri();
        log.debug("TourAPI [detailCommon] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 4. 소개 정보 조회
    @Override
    public String getDetailIntro(String contentId, String contentTypeId) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourDetailIntroUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
                .queryParam("contentId", contentId)
                .queryParam("contentTypeId", contentTypeId);

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [detailIntro] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 5. 반복 정보 조회
    @Override
    public String getDetailInfo(String contentId, String contentTypeId) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourDetailInfoUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
                .queryParam("contentId", contentId)
                .queryParam("contentTypeId", contentTypeId);

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [detailInfo] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 6. 이미지 정보 조회
    @Override
    public String getDetailImage(String contentId) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourDetailImageUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
                .queryParam("contentId", contentId)
                .queryParam("imageYN", "Y"); //이미지조회1 : Y=콘텐츠이미지조회 N=”음식점”타입의음식메뉴이미지
               

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [detailImage] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 7. 분류 코드 조회 (CategoryCode)
    @Override
    public String getDIclsSystmCode2(String cat1, String cat2, String cat3) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourDIclsSystmCodeUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json");
//                .queryParam("numOfRows", "100");

        if (cat1 != null && !cat1.isEmpty()) builder.queryParam("cat1", cat1);
        if (cat2 != null && !cat2.isEmpty()) builder.queryParam("cat2", cat2);
        if (cat3 != null && !cat3.isEmpty()) builder.queryParam("cat3", cat3);

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [categoryCode] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 8. 동기화 목록 조회
    @Override
    public String getAreaBasedSyncList(String areaCode, String modifiedTime) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourAreaBasedSyncListUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json")
//                .queryParam("numOfRows", "20")
                .queryParam("showflag", "1"); // 컨텐츠표출여부(1=표출, 0=비표출)

        if (areaCode != null && !areaCode.isEmpty()) {
            builder.queryParam("areaCode", areaCode);
        }

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [areaBasedSyncList] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    // 9. 법정동 코드 조회
    @Override
    public String getIdongCode(String lDongRegnCd) throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourIdongCodeUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "EnjoyTrip")
                .queryParam("_type", "json");
//                .queryParam("numOfRows", "100");

        if (lDongRegnCd != null && !lDongRegnCd.isEmpty()) {
            builder.queryParam("lDongRegnCd", lDongRegnCd);
        }

        URI uri = builder.build().encode().toUri();
        log.debug("TourAPI [IdongCode] 요청 URL: {}", uri);

        return new RestTemplate().getForObject(uri, String.class);
    }

    @Override
	public String getPetTourInfo(String areaCode, String keyword) throws Exception {

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourPetTourUrl)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json")
	            .queryParam("numOfRows", "20")
	            .queryParam("pageNo", "1");

	    // 지역 필터 (선택)
	    if (areaCode != null && !areaCode.isEmpty()) {
	        builder.queryParam("areaCode", areaCode);
	    }

	    // 키워드 (선택) — 미입력 시 전체 검색
	    if (keyword != null && !keyword.isEmpty()) {
	        builder.queryParam("keyword", keyword);
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [petTourInfo] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * 11. 서비스 분류 코드 조회 (신버전)
	 * 카테고리 코드를 조회 (대분류 -> 중분류 -> 소분류)
	 */
	@Override
	public String getCategoryCode(String contentTypeId, String cat1, String cat2, String cat3) throws Exception {
	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourCategoryCodeUrl)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json");

	    // 선택 파라미터 (값 있을 때만 요청)
	    if (contentTypeId != null && !contentTypeId.isEmpty()) {
	        builder.queryParam("contentTypeId", contentTypeId);
	    }
	    if (cat1 != null && !cat1.isEmpty()) {
	        builder.queryParam("cat1", cat1);
	    }
	    if (cat2 != null && !cat2.isEmpty()) {
	        builder.queryParam("cat2", cat2);
	    }
	    if (cat3 != null && !cat3.isEmpty()) {
	        builder.queryParam("cat3", cat3);
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [categoryCode] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * 12. 좌표 + 지역 기반 복합 목록 조회
	 * 특정 좌표를 중심으로 반경 내 관광지 조회 + 지역 필터링
	 */
	@Override
	public String getAreaBasedList2(String mapX, String mapY, String radius, String contentTypeId, String areaCode) throws Exception {

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourAreaBasedList2Url)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json")
	            .queryParam("numOfRows", "20")
	            .queryParam("pageNo", "1")
	            .queryParam("arrange", "A");  // 제목순

	    // 좌표 기반 필수 입력
	    builder.queryParam("mapX", mapX);     // 경도
	    builder.queryParam("mapY", mapY);     // 위도

	    // radius 없으면 기본 20000m(20km)
	    if (radius == null || radius.isEmpty()) radius = "20000";
	    builder.queryParam("radius", radius);

	    // 선택 필터
	    if (contentTypeId != null && !contentTypeId.isEmpty()) {
	        builder.queryParam("contentTypeId", contentTypeId);
	    }
	    if (areaCode != null && !areaCode.isEmpty()) {
	        builder.queryParam("areaCode", areaCode);
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [areaBasedList2] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * 13. 위치 기반 정보 조회 (내 주변)
	 * 내 위치(좌표)를 중심으로 반경 내 관광지 조회
	 */
	@Override
	public String getLocationBasedList(String mapX, String mapY, String radius, String contentTypeId) throws Exception {

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourLocationBasedListUrl)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json")
	            .queryParam("numOfRows", "20")
	            .queryParam("pageNo", "1")
	            .queryParam("arrange", "E");  // 거리순 정렬 (좌표기반은 E 추천)

	    // 좌표 기반 필수 파라미터
	    builder.queryParam("mapX", mapX);  // 경도
	    builder.queryParam("mapY", mapY);  // 위도

	    // 반경 (기본값 20000m)
	    if (radius == null || radius.isEmpty()) radius = "20000";
	    builder.queryParam("radius", radius);

	    // 선택 파라미터
	    if (contentTypeId != null && !contentTypeId.isEmpty()) {
	        builder.queryParam("contentTypeId", contentTypeId);
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [locationBasedList] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * 14. 키워드 검색 조회
	 * 검색어로 관광지 정보를 통합 검색
	 */
	@Override
	public String searchKeyword(String keyword, String areaCode, String contentTypeId) throws Exception {

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourSearchKeywordUrl)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json")
	            .queryParam("numOfRows", "20")
	            .queryParam("pageNo", "1")
	            .queryParam("arrange", "A"); // 제목순

	    // 🔥 키워드 필수 (없으면 API 에러)
	    builder.queryParam("keyword", keyword);

	    // 선택 파라미터
	    if (areaCode != null && !areaCode.isEmpty()) {
	        builder.queryParam("areaCode", areaCode);
	    }
	    if (contentTypeId != null && !contentTypeId.isEmpty()) {
	        builder.queryParam("contentTypeId", contentTypeId);
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [searchKeyword] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * 15. 행사/축제 정보 조회
	 * 날짜 기준으로 진행되는 축제나 행사 정보를 조회
	 */
	@Override
	public String getFestivalInfo(String eventStartDate, String eventEndDate, String areaCode) throws Exception {

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourFestivalInfoUrl)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json")
	            .queryParam("numOfRows", "20")
	            .queryParam("pageNo", "1")
	            .queryParam("arrange", "A"); // 제목순

	    // 🧨 행사 조회는 eventStartDate 필수
	    // YYYYMMDD 형식
	    builder.queryParam("eventStartDate", eventStartDate);

	    // 종료 날짜가 전달되면 기간 제한 조회
	    if (eventEndDate != null && !eventEndDate.isEmpty()) {
	        builder.queryParam("eventEndDate", eventEndDate);
	    }

	    // 선택 — 지역 필터
	    if (areaCode != null && !areaCode.isEmpty()) {
	        builder.queryParam("areaCode", areaCode);
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [festivalInfo] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * 16. 숙박 정보 조회
	 * 특정 지역이나 좌표 주변의 숙박업소 정보 조회
	 */
	@Override
	public String searchStay(String areaCode, String mapX, String mapY) throws Exception {

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tourStayUrl)
	            .queryParam("serviceKey", serviceKey)
	            .queryParam("MobileOS", "WEB")
	            .queryParam("MobileApp", "EnjoyTrip")
	            .queryParam("_type", "json")
	            .queryParam("numOfRows", "20")
	            .queryParam("pageNo", "1")
	            .queryParam("arrange", "E"); // 거리순 추천

	    // 지역 필터 (선택)
	    if (areaCode != null && !areaCode.isEmpty()) {
	        builder.queryParam("areaCode", areaCode);
	    }

	    // 좌표 기반 검색 (둘 다 들어올 경우 거리순 검색)
	    if (mapX != null && !mapX.isEmpty()) {
	        builder.queryParam("mapX", mapX);
	    }
	    if (mapY != null && !mapY.isEmpty()) {
	        builder.queryParam("mapY", mapY);
	    }

	    // 좌표 기반일 때 반경은 default 20000m
	    if (mapX != null && !mapX.isEmpty() && mapY != null && !mapY.isEmpty()) {
	        builder.queryParam("radius", "20000");
	    }

	    URI uri = builder.build().encode().toUri();
	    log.debug("TourAPI [searchStay] 요청 URL: {}", uri);

	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.getForObject(uri, String.class);
	}
	
	
	

	

	
}