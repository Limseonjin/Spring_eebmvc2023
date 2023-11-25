package com.spring.mvc.chap04.controller;

/*
    # 컨트롤러
    - 클라이언트의 요청을 받아서 처리하고 응답을 제공하는 객체

    # 요청 URL Endpoint
    1. 학생의 성적정보 등록폼 화면을 보여주고
       동시에 지금까지 저장되어 있는 성적 정보 목록을 조회
    - /score/list   :   GET

    2. 학생의 입력된 성적정보를 데이터베이스에 저장하는 요청
    - /score/register   :  POST

    3. 성적정보를 삭제 요청
    - /score/remove    :  GET or POST

    4. 성적정보 상세 조회 요청
    - /score/detail  :   GET
 */

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.dto.ScoreResponseDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap04.repository.ScoreRepositoryImpl;
import com.spring.mvc.chap04.service.ScoreService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/score")
@RequiredArgsConstructor // final이 붙은 필드를 초기화하는 생성자를 생성
//@AllArgsConstructor // 모든 필드를 초기화하는 생성자를 생성
public class ScoreController {

    // 저장소에 의존하여 데이터 처리를 위임한다.
    // 의존객체는 불변성을 가지는 것이 좋다.
    private final ScoreService service;

    // @Autowired // 스프링에 등록된 빈을 자동주입
    // 생성자 주입을 사용하고 생성자가 단 하나 -> autowired 생략가능
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    //1. 성적 입력폼 띄우기 + 목록조회
    // - jsp파일로 입력폼 화면을 띄워줘야 함 (view 포워딩)
    // - 저장된 성적정보 리스트를 jsp에 보내줘야 함 (model에 데이터 전송)
    // - 저장된 성적정보 리스트를 어떻게 가져오느냐 from 데이터베이스
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "num") String sort) {
        System.out.println("/score/list GET !!");

        List<ScoreResponseDTO> dtoList = service.getList(sort);
        model.addAttribute("sList", dtoList);

        return "chap04/score-list";
    }

    //2. 성적정보를 데이터베이스에 저장하는 요청
    @PostMapping("/register")
    public String register(ScoreRequestDTO score) {
        System.out.println("/score/register POST !!");
        System.out.println("score = " + score);

        service.insertScore(score);

        /*
            # forward vs redirect
            - 포워드는 요청 리소스를 그대로 전달해줌.
            - 따라서 URL이 변경되지 않고 한번의요청과 한번의 응답만 이뤄짐

            - 리다이렉트는 요청후에 자동응답이 나가고
              2번째 자동요청이 들어오면서 2번째 응답을 내보냄
            - 따라서 2번째 요청의 URL로 자동 변경됨
         */

        // forward할때는 포워딩할 파일의 경로를 적는것
        // ex) /WEB-INF/views/chap04/score-list.jsp

        // redirect할때는 리다이렉트 요청 URL을 적는것
        // ex) http://localhost:8080/score/detail

        return "redirect:/score/list";
    }

    //3. 성적 삭제 요청
    @RequestMapping(value = "/remove/{stuNum}", method = {RequestMethod.GET, RequestMethod.POST})
    public String remove(HttpServletRequest request,
                         @PathVariable int stuNum) {
        System.out.printf("/score/remove %s !!\n", request.getMethod());
        System.out.println("삭제할 학번: " + stuNum);

        service.deleteScore(stuNum);

        return "redirect:/score/list";
    }

    //4. 성적 상세 조회 요청
    @GetMapping("/detail")
    public String detail(int stuNum, Model model) {
        System.out.println("/score/detail GET !!");

        retrieve(stuNum, model);

        return "chap04/score-detail";
    }

    private void retrieve(int stuNum, Model model) {
        Score score = service.retrieve(stuNum);
        model.addAttribute("s", score);
    }

    // 5. 수정 입력 폼을 열어주는 요청
    // /score/modify : GET
    @GetMapping("/modify")
    public String modify(int stuNum, Model model) {
        System.out.println("/score/modify GET !!");
        retrieve(stuNum, model);
        return "chap04/score-modify";
    }

    // 6. 수정 처리 요청
    // /score/modify : POST
    @PostMapping("/modify")
    public String modify(int stuNum, ScoreRequestDTO dto) {
        System.out.println("/score/modify POST !!");
        service.updateScore(stuNum, dto);

        return "redirect:/score/detail?stuNum=" + stuNum;
    }

}
