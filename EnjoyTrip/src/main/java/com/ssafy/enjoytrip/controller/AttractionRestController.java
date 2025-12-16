package com.ssafy.enjoytrip.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.PageResponse;
import com.ssafy.enjoytrip.service.AttractionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attraction")
@Slf4j

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AttractionRestController {
	
	private final AttractionService attractionService;
	
	// ==========================================
	// [User Part] 기존 구현 기능 (1 ~ 9)
	// ==========================================

	// 1. 지역 기반 관광지 목록 조회
	
	@GetMapping("/list")
	public ResponseEntity<?> getAreaBasedList(
	        @RequestParam(value= "areaCode", required = false) String areaCode,
	        @RequestParam(value = "contentTypeId", required = false) String contentTypeId,
	        @RequestParam(value = "pageNo", defaultValue = "1") String pageNo) { // ★ pageNo 추가

	    log.info("[list] 목록 조회 - area: {}, type: {}, page: {}", areaCode, contentTypeId, pageNo);
	    try {
	        // 서비스에 pageNo도 같이 전달!
	        String result = attractionService.getAreaBasedList(areaCode, contentTypeId, pageNo);
	        return makeResponse(result);
	    } catch(Exception e) {
	        return exceptionHandling(e);
	    }
	}
	// 2. 지역 코드 조회
	@GetMapping("/area")
	public ResponseEntity<?> getAreaCode(@RequestParam(value= "areaCode", required = false) String areaCode) {
		log.info("[area] 지역 코드 조회 요청 - areaCode: {}", areaCode);
		try {
			String result = attractionService.getAreaCode(areaCode);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 3. 공통 상세 정보 조회
	@GetMapping("/detail/{contentId}")
	public ResponseEntity<?> getDetailCommon(@PathVariable("contentId") String contentId) {
		log.info("[detail] 관광지 상세 정보 조회 요청 - contentId: {}", contentId);
		try {
			String result = attractionService.getDetailCommon(contentId);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 4. 소개 정보 조회
	@GetMapping("/intro")
	public ResponseEntity<?> getDetailIntro(
			@RequestParam("contentId") String contentId,
			@RequestParam("contentTypeId") String contentTypeId) {
		log.info("[intro] 소개 정보 조회 요청 - contentId: {}, contentTypeId: {}", contentId, contentTypeId);
		try {
			String result = attractionService.getDetailIntro(contentId, contentTypeId);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 5. 반복 정보 조회
	@GetMapping("/info")
	public ResponseEntity<?> getDetailInfo(
			@RequestParam("contentId") String contentId,
			@RequestParam("contentTypeId") String contentTypeId) {
		log.info("[info] 반복 정보 조회 요청 - contentId: {}, contentTypeId: {}", contentId, contentTypeId);
		try {
			String result = attractionService.getDetailInfo(contentId, contentTypeId);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 6. 이미지 정보 조회
	@GetMapping("/image")
	public ResponseEntity<?> getDetailImage(@RequestParam("contentId") String contentId) {
		log.info("[image] 이미지 정보 조회 요청 - contentId: {}", contentId);
		try {
			String result = attractionService.getDetailImage(contentId);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 7. 분류 코드 조회 (기존 User 버전)
	@GetMapping("/DIclsSystmCode")
	public ResponseEntity<?> getCategoryCodeLegacy(
			@RequestParam(value = "cat1", required = false) String cat1,
			@RequestParam(value = "cat2", required = false) String cat2,
			@RequestParam(value = "cat3", required = false) String cat3) {
		log.info("[DIclsSystmCode] 분류 코드 조회 요청 - cat1: {}, cat2: {}, cat3: {}", cat1, cat2, cat3);
		try {
			// Service 메서드 이름이 'IclsSystmCode2'라면 그걸로 수정해서 호출하세요.
			// 여기서는 표준화된 이름인 getCategoryCode를 호출합니다.
			String result = attractionService.getDIclsSystmCode2(cat1, cat2, cat3); 
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 8. 동기화 목록 조회
	@GetMapping("/sync")
	public ResponseEntity<?> getAreaBasedSyncList(
			@RequestParam(value = "areaCode", required = false) String areaCode,
			@RequestParam(value = "modifiedTime", required = false) String modifiedTime) {
		log.info("[sync] 동기화 목록 조회 요청 - areaCode: {}, modifiedTime: {}", areaCode, modifiedTime);
		try {
			String result = attractionService.getAreaBasedSyncList(areaCode, modifiedTime);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 9. 법정동 코드 조회
	@GetMapping("/idong")
	public ResponseEntity<?> getIdongCode(@RequestParam(value = "lDongRegnCd", required = false) String lDongRegnCd) {
		log.info("[idong] 법정동 코드 조회 요청 - lDongRegnCd: {}", lDongRegnCd);
		try {
			// Service 메서드 이름이 'IdongCode'라면 그걸로 수정해서 호출하세요.
			String result = attractionService.getIdongCode(lDongRegnCd);
			return makeResponse(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	// 10. 카테고리 코드 조회 
	@GetMapping("/category")
	public ResponseEntity<?> getCategoryCode(
	        @RequestParam(required = false) String contentTypeId, 
	        @RequestParam(required = false) String cat1,
	        @RequestParam(required = false) String cat2,
	        @RequestParam(required = false) String cat3
	) {
	    log.info("[category] 카테고리 코드 조회 요청 - ctId: {}, cat1: {}, cat2: {}, cat3: {}", 
	            contentTypeId, cat1, cat2, cat3);
	    try {
	        String result = attractionService.getCategoryCode(contentTypeId,cat1, cat2, cat3);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	// 11. 좌표 + 지역 기반 목록 조회
	@GetMapping("/list2")
	public ResponseEntity<?> getAreaBasedList2(
	        @RequestParam String mapX,
	        @RequestParam String mapY,
	        @RequestParam(required = false) String radius,
	        @RequestParam(required = false) String contentTypeId,
	        @RequestParam(required = false) String areaCode
	) {
	    log.info("[list2] 좌표+지역 기반 조회 요청 - mapX: {}, mapY: {}", mapX, mapY);
	    try {
	    	// Service에 getAreaBasedList2 메서드가 있어야 함
	        String result = attractionService.getAreaBasedList2(mapX, mapY, radius, contentTypeId, areaCode);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	// 12. 위치 기반 목록 조회
	@GetMapping("/location")
	public ResponseEntity<?> getLocationBasedList(
	        @RequestParam String mapX,
	        @RequestParam String mapY,
	        @RequestParam(required = false) String radius,
	        @RequestParam(required = false) String contentTypeId
	) {
	    log.info("[location] 위치 기반 관광 조회 요청 - mapX: {}, mapY: {}", mapX, mapY);
	    try {
	    	// Service에 getLocationBasedList 메서드가 있어야 함
	        String result = attractionService.getLocationBasedList(mapX, mapY, radius, contentTypeId);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	// 13. 키워드 검색
	@GetMapping("/search")
	public ResponseEntity<?> searchKeyword(
	        @RequestParam String keyword,
	        @RequestParam(required = false) String areaCode,
	        @RequestParam(required = false) String contentTypeId
	) {
	    log.info("[search] 키워드 검색 요청 - keyword: {}", keyword);
	    try {
	    	// Service에 searchKeyword 메서드가 있어야 함
	        String result = attractionService.searchKeyword(keyword, areaCode, contentTypeId);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	// 14. 행사 정보 조회
	@GetMapping("/festival")
	public ResponseEntity<?> getFestivalInfo(
	        @RequestParam String eventStartDate,
	        @RequestParam(required = false) String eventEndDate,
	        @RequestParam(required = false) String areaCode
	) {
	    log.info("[festival] 행사 정보 조회 요청 - start: {}, end: {}", eventStartDate, eventEndDate);
	    try {
	    	// Service에 getFestivalInfo 메서드가 있어야 함
	        String result = attractionService.getFestivalInfo(eventStartDate, eventEndDate, areaCode);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	// 15. 반려동물 여행 정보
	@GetMapping("/pet")
	public ResponseEntity<?> getPetTourInfo(
	        @RequestParam(required = false) String areaCode,
	        @RequestParam(required = false) String keyword
	) {
	    log.info("[pet] 반려동물 여행 정보 조회 요청 - keyword: {}", keyword);
	    try {
	    	// Service에 getPetTourInfo 메서드가 있어야 함
	        String result = attractionService.getPetTourInfo(areaCode, keyword);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	// 16. 숙박 정보 조회
	@GetMapping("/stay")
	public ResponseEntity<?> searchStay(
	        @RequestParam(required = false) String areaCode,
	        @RequestParam(required = false) String mapX,
	        @RequestParam(required = false) String mapY
	) {
	    log.info("[stay] 숙박 조회 요청 - areaCode: {}", areaCode);
	    try {
	    	// Service에 searchStay 메서드가 있어야 함
	        String result = attractionService.searchStay(areaCode, mapX, mapY);
	        return makeResponse(result);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	

	// --- Helper Method ---
	
	/**
	 * JSON 응답 생성 및 UTF-8 헤더 설정 중복 제거용 메서드
	 */
	private ResponseEntity<String> makeResponse(String result) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
		return new ResponseEntity<>(result, headers, HttpStatus.OK);
	}

	// 공통 예외 처리
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}