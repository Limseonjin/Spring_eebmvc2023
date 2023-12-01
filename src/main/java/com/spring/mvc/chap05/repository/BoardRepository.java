package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Board;

import java.util.List;

public interface BoardRepository {
    // 1.글 목록 전체조회
    List<Board> findAll();
    // 2.글 삽입
    boolean save(Board board);
    // 3.글 삭제
    boolean deleteByNo(int boardNo);
    // 4.글 상세 보기
    Board findOne(int boardNo);

}
