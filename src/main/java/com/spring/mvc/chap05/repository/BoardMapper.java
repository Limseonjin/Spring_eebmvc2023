package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.common.Search;
import com.spring.mvc.chap05.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 1.글 목록 전체조회
    List<Board> findAll(Search page);
    // 2.글 삽입
    boolean save(Board board);
    // 3.글 삭제
    boolean deleteByNo(int boardNo);
    // 4.글 상세 보기
    Board findOne(int boardNo);
    // 조회수 상승
    void updateViewCount(int boardNo);

    //총 게시물 수 구하기
    int count(Search search);
}
