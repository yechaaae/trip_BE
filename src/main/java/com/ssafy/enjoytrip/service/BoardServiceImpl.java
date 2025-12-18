package com.ssafy.enjoytrip.service;

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
    public BoardDto getArticle(int boardId) throws Exception {
        // [비즈니스 로직]
        // 상세 글을 보기 전에 조회수를 1 올린다.
        boardMapper.updateHit(boardId);
        
        // 그 다음 글 내용을 가져온다.
        return boardMapper.getArticle(boardId);
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

	
}