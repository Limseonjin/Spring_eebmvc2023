package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.spring.mvc.chap05.service.LoginResult.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberSerivceTest {

    @Autowired
    MemberSerivce memberSerivce;

    @Test
    @DisplayName("회원정보를 전달하면 비밀번호가 암호화되어 DB에 저장된다.")
    void joinTest() {
        //given
        SignUpRequestDTO dto = SignUpRequestDTO.builder()
                .account("kitty")
                .password("kkk1234!")
                .name("헬로키팅")
                .email("kitty@gmail.com")
                .build();
        //when
        boolean flag = memberSerivce.join(dto, savePath);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("계정명이 kitty인 회원의 로그인 시도 결과를 상황별로 검증")
    void loginTest() {
        //given
        LoginRequestDTO kitty = LoginRequestDTO.builder()
                .account("kitt")
                .password("kkk1234!")
                .build();
        //when
        LoginResult authenticate = memberSerivce.authenticate(kitty,null,null);
        //then
        assertEquals(NO_ACC,authenticate);
    }
}