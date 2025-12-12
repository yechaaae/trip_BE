package com.ssafy.enjoytrip.service;

public interface AttractionService {
	
	/**
	 * 1. 지역 기반 관광지 목록 조회
	 * @param areaCode 지역 코드 (서울=1, 등)
	 * @param contentTypeId 관광지 타입 (12=관광지, 39=음식점 등)
	 */
	String getAreaBasedList(String areaCode, String contentTypeId) throws Exception;
	
	/**
	 * 2. 지역 코드 조회 (시도 / 구군)
	 * @param areaCode 값이 없으면 '시도' 목록, 있으면 해당 시도의 '구군' 목록 반환
	 */
	String getAreaCode(String areaCode) throws Exception;
	
	/**
	 * 3. 공통 상세 정보 조회
	 * 관광지의 기본 정보, 개요(Overview), 좌표, 대표 이미지 등을 조회
	 * @param contentId 관광지 고유 ID
	 */
	String getDetailCommon(String contentId) throws Exception;
	
	/**
	 * 4. 소개 정보 조회
	 * 관광지 타입별 상세 소개 정보 (휴무일, 개장시간, 주차시설 등)
	 */
	String getDetailIntro(String contentId, String contentTypeId) throws Exception;
	
	/**
	 * 5. 반복 정보 조회
	 * 여행 코스나 등산로 같이 반복되는 상세 정보를 조회
	 */
	String getDetailInfo(String contentId, String contentTypeId) throws Exception;
	
	/**
	 * 6. 이미지 정보 조회
	 * 해당 관광지의 추가 이미지 목록 조회
	 */
	String getDetailImage(String contentId) throws Exception;
	
	/**
	 * 7. 서비스 분류 코드 조회 (구버전/Legacy)
	 * 대/중/소분류 코드를 계층적으로 조회
	 */
	String getDIclsSystmCode2(String cat1, String cat2, String cat3) throws Exception;
	
	/**
	 * 8. 관광정보 동기화 목록 조회
	 * 특정 시점 이후 변경된 관광지 정보를 조회 (데이터 갱신용)
	 */
	String getAreaBasedSyncList(String areaCode, String modifiedTime) throws Exception;
	
	/**
	 * 9. 법정동 코드 조회
	 * 행정구역(읍면동) 코드를 조회
	 */
	String getIdongCode(String lDongRegnCd) throws Exception;
	
	/**
	 * 10. 반려동물 동반 여행 정보 조회
	 * 반려동물과 함께 갈 수 있는 여행지 정보 조회
	 */
	String getPetTourInfo(String areaCode, String keyword) throws Exception;
	
	/**
	 * 11. 서비스 분류 코드 조회 (신버전/Standard)
	 * 카테고리 코드를 조회 (대분류 -> 중분류 -> 소분류)
	 */
	String getCategoryCode(String contentTypeId, String cat1, String cat2, String cat3) throws Exception;
	
    /**
     * 12. 좌표 + 지역 기반 복합 목록 조회
     * 특정 좌표(mapX, mapY)를 중심으로 반경(radius) 내의 관광지를 조회하되, 
     * 지역(areaCode) 필터링도 함께 적용
     */
    String getAreaBasedList2(String mapX, String mapY, String radius,
            String contentTypeId,
            String areaCode
    ) throws Exception;
    
    /**
     * 13. 위치 기반 정보 조회 (내 주변)
     * 내 위치(좌표)를 중심으로 반경 내 관광지 조회 (GPS 기반)
     */
    String getLocationBasedList(
            String mapX,
            String mapY,
            String radius,
            String contentTypeId
    ) throws Exception;
    
    /**
     * 14. 키워드 검색 조회
     * 검색어(keyword)로 관광지 정보를 통합 검색
     */
    String searchKeyword(
            String keyword,
            String areaCode,
            String contentTypeId
    ) throws Exception;
    
    /**
     * 15. 행사/축제 정보 조회
     * 날짜(시작일~종료일) 기준으로 진행되는 축제나 행사 정보를 조회
     */
    String getFestivalInfo(String eventStartDate, String eventEndDate, String areaCode) throws Exception;

    /**
     * 16. 숙박 정보 조회
     * 특정 지역이나 좌표 주변의 숙박업소(호텔, 펜션 등) 정보 조회
     */
    String searchStay(
            String areaCode,
            String mapX,
            String mapY
    ) throws Exception;
    
}

/* [참고] 지역 코드(areaCode) 매핑 테이블
	1 : 서울
	2 : 인천
	3 : 대전
	4 : 대구
	5 : 광주
	6 : 부산
	7 : 울산
	8 : 세종특별자치시
	9 : 경기도
	10 : 강원특별자치도 (32번인 경우도 있음, API 버전에 따라 다름)
	11 : 충청북도
	12 : 충청남도
	13 : 경상북도
	14 : 경상남도
	15 : 전북특별자치도
	16 : 전라남도
	17 : 제주특별자치도
	31 : 경기도 (일부 API)
	32 : 강원도 (일부 API)
	(주의: API 버전(KorService1/2)에 따라 경기도가 31번, 강원도가 32번일 수 있으니 getAreaCode로 확인 필요)
*/