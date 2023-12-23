package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.service.MemberSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {
    private final MemberSerivce memberSerivce;

    //회원 가입 양식 요청
    @GetMapping("/sign-up")
    public String signUp(){
        log.info("/members/sign-up GET : forwarding to sign-up.jsp");
        return "members/sign-up";
    }

    //아이디 이메일 중복체크 비동기 요청처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check (String type, String keyword){
        log.info("/members/check?type={}&keyword={} ASYNC GET",type,keyword);
        boolean flag = memberSerivce.checkDuplicateValue(type, keyword);
        log.debug("중복체크 결과 : {}",flag);
        return ResponseEntity.ok().body(flag);
    }

    //회원가입 처리
    @PostMapping("/sign-up")
    public String signUps(SignUpRequestDTO dto){
        log.info("members/sign-up : POST!");
        log.debug("parameter : {}",dto);
        boolean flag = memberSerivce.join(dto);
        return flag ? "redirect:/board/list" : "redirect:/members/sign-up";
    }
}
