package com.ssafy.enjoytrip.dto;

import java.util.List;
import lombok.Data;

@Data
public class CommentDto {
	private int commentId;
	private int boardId;
	private String content;
	private String userId;
	private String createdAt;
	
	private Integer parentId;
	private int isDeleted;
	
	private String nickName;
	
	private List<CommentDto> children;
	
}
