package com.ssafy.enjoytrip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private int mno; // PK(유저 테이블 아이디)
	private String userId; // 로그인용 아이디
	private String password; // 비밀번호
	private String email; // 이메일
	private String nickName; // 익명성 보장을 위해 이름 대신 닉네임 입력 받음
	private String phoneNumber; // 전화번호
	private String birthDate; // 생년월일 "1999-01-01" 형식 준수
	private String sex; // M 남자, F 여자 + 추후 매칭 서비스나 확장성 위해
	private String createdAt; // 가입 날짜
	
	// 선택 사항 (관리자 일반 유저 구분)
	// private int role; // 0: 일반, 1: 관리자
}
