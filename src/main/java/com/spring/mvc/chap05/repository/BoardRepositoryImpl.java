package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Board;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BoardRepositoryImpl implements BoardRepository {
    private static final Map<Integer,Board> boardMap;
    private static int sequence;
    static {
        boardMap = new HashMap<>();
//        Board data1 = new Board(++sequence, "반가워횽", "안녕하쎄욘", 5, LocalDateTime.now());
//        Board data2 = new Board(++sequence, "이쒸", "안녕하쎄욘", 0, LocalDateTime.now());
//        Board data3 = new Board(++sequence, "우애잉", "안녕하쎄욘", 0, LocalDateTime.now());
//
//        boardMap.put(data1.getBoardNo(),data1);
//        boardMap.put(data2.getBoardNo(),data2);
//        boardMap.put(data3.getBoardNo(),data3);

    }

    @Override
    public List<Board> findAll() {
        return boardMap.values()
                .stream()
                .sorted(Comparator.comparing(Board::getBoardNo).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(Board board) {
        board.setBoardNo(++sequence);
        boardMap.put(board.getBoardNo(),board);
        return true;
    }

    @Override
    public boolean deleteByNo(int boardNo) {
        boardMap.remove(boardNo);
        return true;
    }

    @Override
    public Board findOne(int boardNo) {
        return  boardMap.get(boardNo);

    }
}
