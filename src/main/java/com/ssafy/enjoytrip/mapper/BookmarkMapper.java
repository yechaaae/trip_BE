package com.ssafy.enjoytrip.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.enjoytrip.dto.BookmarkDto;

@Mapper
public interface BookmarkMapper {
    // 1. 찜 하기 (저장)
    void addBookmark(BookmarkDto bookmarkDto);

    // 2. 내 찜 목록 보기
    List<BookmarkDto> listMyBookmark(String userId);

    // 3. 찜 취소 (삭제)
    void deleteBookmark(int bookmarkId);
}