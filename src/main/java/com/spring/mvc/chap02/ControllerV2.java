package com.spring.mvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/model")
public class ControllerV2 {

    //1. Model 객체
    // 자바가 갖고있는 데이터를 JSP로 넘겨줄 때 사용하는 바구니같은 역할
    @GetMapping("/hobbies")
    public String hobbies(Model model){
        System.out.println("취미 하이~");
        String name = "쨱짹쓰";
        List<String> hobbyList = List.of("전깃술 앉아있기", "좁쌀주워먹기", "노래부르기");

        model.addAttribute("userName",name);
        model.addAttribute("hobbies",hobbyList);
        return "chap02/hobbies";
    }
    @GetMapping("/hobbies2")
    public String hobbies2(){
        System.out.println("취미 하이~");
        return "";
    }
}
