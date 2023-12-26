package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.service.LoginResult;
import com.spring.mvc.chap05.service.MemberSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 로그인 양식 요청
    @GetMapping("/sign-in")
    public String SignIn(){
        log.info("/members/sign-in GET - forwarding to sign-in.jsp");

        return "/members/sign-in";
    }

    // 로그인 검증 요청
    @PostMapping("/sign-in")
    public String signIn(
            LoginRequestDTO dto
            //Model에 담긴 데이터는 redirect시 jsp로 전달 X
            // redirect는 요청이 2번 들어가서 첫번째 요청시 보관한 데이터가 소실됨
            , RedirectAttributes ra
    ){
        log.info("/memebers/sign-in POST!");
        log.info("parameter : {}",dto);

        LoginResult authenticate = memberSerivce.authenticate(dto);
        log.debug("login result : {} ",authenticate);

//        model.addAttribute("msg",authenticate);
        ra.addFlashAttribute("msg",authenticate);

        if (authenticate == LoginResult.SUCCESS){ //로그인 성공시
            return "redirect:/board/list";
        }
        return "redirect:/members/sign-in";
    }
}