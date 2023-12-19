package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.common.Search;
import com.spring.mvc.chap05.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {
    @Autowired
    BoardMapper mapper;
//    @Test
//    @DisplayName("게시물 300개를 저장해야한다.")
//    void bulkInsertTest() {
//        //given
//        for (int i = 0; i < 300; i++) {
//            Board b = Board.builder()
//                    .content("테스트용 내용+"+i)
//                    .title("테스트용 제목+ "+i)
//                    .build();
//            mapper.save(b);
//        }
//        //when
//
//        //then
//    }
    @Test
    @DisplayName("게시물 전체조회하면 300개의 게시물이 조회댄다.")
    void findAll(){
        //when
        List<Board> all = mapper.findAll(new Search());
        //then
        assertEquals(300,all.size());
    }
    @Test
    @DisplayName("게시물 30번 게시물을 조회하면 제목에 29이 포함되어 있어야한다.")
    void findOneTest(){
        //given
        int boardNo = 30;
        //when
        Board b = mapper.findOne(boardNo);
        //then
        assertTrue(b.getTitle().contains("29"));
    }
    @Test
    @DisplayName("게시물 29번 게시물을 삭제하고 조회하면 조회되지 않아야 한다.")
    @Transactional
    @Rollback
    void deleteTest(){
        //테스트는 몇번을 돌려도같은 결과가 나와야함.
        //given
        int boardNo = 29;
        //when
        boolean flag = mapper.deleteByNo(boardNo);
        Board b = mapper.findOne(boardNo);
        //then
        assertTrue(flag);
        assertNull(b);
    }

}