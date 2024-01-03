package com.spring.mvc.chap05.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SnsLoginController
{
    @Value("${sns.kakao.app-key}")
    private String kakaoAppKey;
    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRedirectURL;

    // 카카오 인가코드 발급요청
    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        log.info("/kakao/login : GET!");
        String uri = "https://kauth.kakao.com/oauth/authorize";
        uri += "?client_id=" +kakaoAppKey;
        uri += "&redirect_uri=" + kakaoRedirectURL;
        uri += "&response_type=code";
        return "redirect:"+uri;
    }

    // 카카오 인가코드 받기
    @GetMapping("auth/kakao")
    public String snsKakao(String code){
        log.info("카카오 로그인 인가 코드 : {}",code);
        return "redirect:/members/sign-in";
    }
}
