package com.spring.mvc.chap01;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    //3.DTO(Data Tranfer Object)객체 사용하기
    // -> 파라미터의 양이 엄청 많거나 서로 연관되어 있는 경우에 사용
    // ex : /order/orderNum=123&goodsName=구두&amount=3&price=2000...
    @RequestMapping("/order")
    public String order(OrderRequestDTO dto){
        System.out.println("dto = " + dto);
        System.out.println("dto.getGoodsName() = " + dto.getGoodsName());
        return "";
    }

    // 4. URL경로에 붙어 있는 데이터 읽기
    // ex: /member/kim/107
    @RequestMapping("/member/{userName}/{userNo}")
    public String member(
            @PathVariable String userName,
            @PathVariable  int userNo){
        System.out.println("userName = " + userName);
        System.out.println("userNo = " + userNo);
        return "";
    }

    // 5.POST요청 데이터 읽기
    // -> food.jsp에서 보낸 데이터를 읽을 것임
    // 주소창에 보이는 것은 없지만 읽을 수 있음
    // PostMapping  <= POST로만 보내는걸 허용함
    @PostMapping("/food-select")
    public String select(String foodName, String category){
        System.out.println("foodName = " + foodName);
        System.out.println("category = " + category);
        return "index";
    }
}
