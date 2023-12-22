package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PasswordEncoder encoder;
    @Test
    @DisplayName("회원가입에 성공해야한다")
    void saveTest() {
        //given
        Member build = Member.builder()
                .account("kuromi")
                .password(encoder.encode("aaa1234!"))
                .name("쿠로밍")
                .email("kuromi@naver.com")
                .build();
        //when
        boolean save = memberMapper.save(build);
        //then
        assertTrue(save);
    }
    @Test
    @DisplayName("lesserafim계정을 조회하면 그 회원이 이름이 라잉엉이여야 한다")
    void findMemberTest() {
        //given
        String acc = "leserrafim";
        //when
        Member member = memberMapper.findMember(acc);
        //then
        assertEquals("라잉엉",member.getName());
    }

    @Test
    @DisplayName("계정이 newjeans일 경우 중복확인 결과는 false여야한다")
    void duplicateTest() {
        //given
        String acc = "newjeans";
        //when
        boolean account = memberMapper.isDuplicate("account", acc);
        //then
        assertFalse(account);
    }

    @Test
    @DisplayName("이메일이 abc@naver.com 경우 중복확인 결과는 true여야한다")
    void duplicateEmailTest() {
        //given
        String email = "abc@naver.com";
        //when
        boolean f = memberMapper.isDuplicate("email", email);
        //then
        assertTrue(f);
    }

    @Test
    @DisplayName("비밀번호가 암호화 되어야한다")
    void endcodinTest() {
        //인코딩 전 패스워드
        String rawPassword = "abc1234!@";

        //인코딩 후 패스워드
        String encode = encoder.encode(rawPassword);
        System.out.println("rawPassword = " + rawPassword);
        System.out.println("encode = " + encode);
    }
}