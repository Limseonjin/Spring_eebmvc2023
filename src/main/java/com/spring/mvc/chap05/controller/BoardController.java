package com.spring.mvc.chap05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    // 1. 목록 조회 요청 (/board/list : GET)
    // 2. 글쓰기 화면 요청 (/board/write : GET)
    // 3. 글쓰기 등록요청 (/board/write : POST)
    // 4. 글 삭제 요청 (/board/delete : GET)
    // 5. 글 상세보기 요청 (/board/detail : GET)

}
