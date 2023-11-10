package com.spring.mvc.chap01;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//컨트롤러: 클라이언트의 요청을 받아서 처리 후 응답을 보내주는 역할
@Controller //빈 등록 : 클래스의 객체 생성 관리는 스프링이 처리 하겠다
public class ControllerV1 {
    // 세부요청처리는 메서드를 통해 등록

    @RequestMapping("/")
    public String home(){
        System.out.println("Welcome to my home");

        // 리턴문에는 어떤 jsp로 포워딩할지 경로를 적는다.
        return "index";
    }
    @RequestMapping("/food")
    public String food(){
        System.out.println("Welcome to my food");

        // 리턴문에는 어떤 jsp로 포워딩할지 경로를 적는다.
        return "chap01/food";
    }

    // 요청 파라미터 읽기 ( 클라이언트가 보낸 정보 )
    // 1.HttpServletRequest객체 이용

    //ex: /person?name=kim&age=30
    @RequestMapping("/person")
    public String person(HttpServletRequest request){
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("name = " + name);
        System.out.println("age = " + age);

        return "";
    }

    //2. @RequestParam 사용
    // 요청값과 함수의 매개변수가 달라도 값을 읽을 수 있게함
    // 디폴트값도 설정 가능
    //ex : /major?stu=park&major=business&grade=3
    @RequestMapping("/major")
    public String major(String stu,
                        @RequestParam("major") String mj,
                        @RequestParam(defaultValue = "1") Integer grade){
        System.out.println("stu = " + stu);
        System.out.println("major = " + mj);
        System.out.println("grade = " + grade);

        // 리턴문에는 어떤 jsp로 포워딩할지 경로를 적는다.
        return "";
    }
}
