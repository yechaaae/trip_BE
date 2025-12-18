package com.ssafy.enjoytrip.dto;

import lombok.Data;

@Data
public class FollowDto {
	private String fromUserId;
	private String toUserId;
	private String createdAt;
	
	// 조인을 위해 필요한 상대방 추가 정보(프론트 엔드 화면 표시용)
	private String nickName;
	private String profileImg;
}
