package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.dto.CommentDto;
import com.ssafy.enjoytrip.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentMapper commentMapper;
	
	@Override
	public void writeComment(CommentDto commentDto) throws SQLException {
		commentMapper.writeComment(commentDto);
		
	}

	@Override
	public void deleteComment(int commentId, String userId) throws SQLException {
		int result = commentMapper.deleteComment(commentId, userId);
        
        if (result == 0) {
            // DB에 업데이트된 줄이 0개라는 뜻은 -> 내 글이 아니거나, 이미 없는 글임
            throw new SQLException("삭제 권한이 없거나 존재하지 않는 댓글입니다.");
        }
		
	}

	@Override
	public void modifyComment(CommentDto commentDto) throws SQLException {
		int result = commentMapper.modifyComment(commentDto);
        
        if (result == 0) {
            throw new SQLException("수정 권한이 없거나 존재하지 않는 댓글입니다.");
        }
		
	}

	@Override
	public List<CommentDto> listComments(int boardId) throws SQLException {
		// 1. DB에서 평탄한 리스트(Flat List) 조회
		List<CommentDto> flatList = commentMapper.listComments(boardId);
		
		// 2. 결과를 담을 리스트(최상위 댓글만 담김)
		List<CommentDto> rootList = new ArrayList<>();
		
		// 3. 부모 댓글을 빠르게 찾기 위해 Map에 담기 (Key: commentId, Value : Dto)
		Map<Integer, CommentDto> map = new HashMap<>();
		
		// 4. Map 초기화 및 Children 리스트 초기화
		for(CommentDto dto : flatList) {
			map.put(dto.getCommentId(), dto);
			if(dto.getChildren() == null) {
				dto.setChildren(new ArrayList<>());
			}
		}
		
		// 5. 트리 구조 조립(부모- 자식 연결)
		for(CommentDto dto: flatList) {
			Integer parentId = dto.getParentId();
			
			// 5-1. 최상위 댓글이 경우 (parentId가 0이거나 null)
			if(parentId == null || parentId == 0) {
				rootList.add(dto);
			}
			
			// 5-2. 대댓글인 경우(부모를 찾아 자식 리스트에 추가)
			else {
				CommentDto parent = map.get(parentId);
				if(parent!=null) {
					parent.getChildren().add(dto);
				}else {
					// 부모가 삭제되거나 없는데 자식만 남은 경우 , 
                    // 일단 최상위에 보여주거나 제외하는 정책을 결정해야 함. 
                    // 여기서는 최상위 리스트에 추가합니다.
					
					rootList.add(dto);
				}
			}
			
		}
		
		
		return rootList;
	}
    @Override
    public void deleteCommentByAdmin(int commentId) throws SQLException {
        commentMapper.deleteCommentByAdmin(commentId);
    }
	

}
