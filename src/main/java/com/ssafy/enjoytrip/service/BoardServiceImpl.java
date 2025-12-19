package com.ssafy.enjoytrip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.mapper.BoardMapper;



@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    @Transactional // 글 작성 도중 에러나면 자동 롤백 (안전 장치)
    public void writeArticle(BoardDto boardDto) throws Exception {
        // 컨트롤러에서 넘어온 데이터(파일 정보 포함)를 DB에 저장
        boardMapper.writeArticle(boardDto);
    }

    @Override
    public List<BoardDto> listArticle(Map<String, Object> map) throws Exception {
        return boardMapper.listArticle(map);
    }

    @Override
    public BoardDto getArticle(int boardId, String userId, boolean updateHit) throws Exception {
        // [비즈니스 로직]
        // 상세 글을 보기 전에 조회수를 1 올린다.
    	if(updateHit) {
    		boardMapper.updateHit(boardId);
    	}
        
        
        // 그 다음 글 내용을 가져온다.
        return boardMapper.getArticle(boardId, userId);
    }

    @Override
    @Transactional
    public void modifyArticle(BoardDto boardDto) throws Exception {
        // TODO: (선택) 여기서 작성자 본인이 맞는지 체크하는 로직이 들어갈 수 있습니다.
        boardMapper.modifyArticle(boardDto);
    }

    @Override
    @Transactional
    public void deleteArticle(int boardId) throws Exception {
        // TODO: (선택) 파일 삭제 로직이 필요하다면 여기서 로컬 파일도 지워야 합니다.
        boardMapper.deleteArticle(boardId);
    }

    @Override
    public Map<String, Object> getReviewStats(int contentId) {
        Map<String, Object> stats = boardMapper.getReviewStats(contentId);

        // 혹시 null로 넘어오는 케이스 방어 (리뷰 0개면 avg가 null일 수 있음)
        if (stats == null) stats = new HashMap<>();
        if (stats.get("reviewCount") == null) stats.put("reviewCount", 0);
        if (stats.get("avgRating") == null) stats.put("avgRating", 0);

        return stats;
    }
    
    @Override
    @Transactional // 트랜잭션 필수
    public void toggleLike(int boardId, String userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("boardId", boardId);
        map.put("userId", userId);
        
        // 1. 이미 좋아요를 눌렀는지 확인
        int count = boardMapper.checkLike(map);
        
        if (count > 0) {
            // 2-A. 이미 눌렀다면 -> 삭제 (Unlike)
            boardMapper.deleteLike(map);
        } else {
            // 2-B. 안 눌렀다면 -> 추가 (Like)
            boardMapper.addLike(map);
        }
        
        // 3. board 테이블의 전체 좋아요 수 갱신 (동기화)
        boardMapper.updateLikeCount(boardId);
    }
    
    @Override
    public List<BoardDto> listLikedArticle(String userId) throws Exception {
        return boardMapper.listLikedArticle(userId);
    }
	
}