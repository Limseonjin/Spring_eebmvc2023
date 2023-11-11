package com.spring.mvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    // 2. ModelAndView 객체 사용

    @GetMapping("/hobbies2")
    public ModelAndView hobbies2(){
        System.out.println("취미 하이~");
        String name = "냥냥이";
        List<String> hList = List.of("사마귀랑 놀기", "낮잠자기");

        // jsp로 보낼 데이터를 ModelAndView에 담아야함
        ModelAndView mav = new ModelAndView();
        mav.addObject("userName",name);
        mav.addObject("hobbies",hList);

        //view의 다이터를 따로 담아야함
        mav.setViewName("chap02/hobbies");

        return mav;
    }
}
