package com.spring.mvc.chap04.controller;

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.dto.ScoreResponseDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap04.repository.ScoreRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/*
*  # 컨트롤러
*  - 클라이언트의 요청을 받아서 처리하고 응답을 제공하는 객체
*
*  # 요청 URL Endpoint
*  1. 학생의 성적정보 등록폼 화면을 보여주고
*  동시에 지금까지 저장되어 있는 성적 정보 목록을 조회
*  - /score/list : GET
*
*  2. 학생의 입력된 성적정보를데이터베이스에 저장하는 요청
*  - /score/register : POST
*
*  3. 성적정보를 삭제 하는 요청
*  - /score/remove : GET or POST
*
*  4. 성적정보 상세 조회 요청
*  - /score/detail : GET
* */
@Controller
@RequestMapping("/score")
@RequiredArgsConstructor //final이 붙은 필드를 초기화하는 생성자를 생성
//@AllArgsConstructor //모든 필드를 초기화하는 생성자를 생성(final도 초기화)
public class ScoreController {
    //저장소에 의존하여 데이터처리를 위임한다.
    //의존 객체는 불변성을 가지는 것이 좋다 (final)
    private final ScoreRepository repository;

//    @Autowired //스프링에 등록된 빈을 자동주입
//    //생성자 주입을 사용하고 생성자가 단 하나 -> autowired 생략가능
//    public ScoreController(ScoreRepository repository){
//        this.repository = repository;
//    }

    //1. 성적정보 폼 + 조회
    // -jsp파일로 입력폼 화면을 띄워야함
    // - 저장된 성적정보 리스트를 jsp에 보내줘야함 (model에 데이터 정송)
    // - 저장된 성적정보 리스트를 어떻게 가져오느냐 (데이터베이스)
    @GetMapping("/list")
    public String list(Model model,@RequestParam(defaultValue = "num") String sort){
        System.out.println("/list/ GET~");

        //db에서 조회한 모든 데이터
        List<Score> scoreList = repository.findAll(sort);
        System.out.println("scoreList = " + scoreList);

        //클라이언트가 필요한 일부 데이터
//        List<ScoreResponseDTO> dtoList = new ArrayList<>();
//        for (Score score : scoreList) {
//            dtoList.add(new ScoreResponseDTO(score));
//        }
        List<ScoreResponseDTO> dtoList = scoreList.stream()
                .map(ScoreResponseDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("sList",dtoList);
        return "chap04/score-list";
    }
    //2.성적 정보를 DB에 저장하는 요청
    @PostMapping("/register")
    public String register(ScoreRequestDTO score){
        System.out.println("/register/ PoST~");
        System.out.println("score = " + score);

        // DTO를 엔터티로 변환 -> 데이터 생성
        Score savedScore = new Score(score);
        repository.save(savedScore);
        /*
        *  forward vs Redirect
        * -forawrd는 요청 리소스 그대로 전달 > URL이 변경되지 않고 한번의 요청과 한번의 응답만 이뤄짐
        * -reDirect는 요충 후 자동응답, 2번째부터 자동요청이 들어오면서 2번째 응답을 보냄 >자동URL변경
        * */
        // forward할때는 포워딩할 파일의 경로를 적는것 ex) WEB-INF/views/chap04/score-list.jsp
        // redirect할때는 리다이렉트 요청 URL을 적는 것 ex)http://localthost:8181/score/detail
        return "redirect:/score/list";
    }
    //3. 성적정보를 삭제하는 요청
    @RequestMapping(value = "/remove/{stuNum}", method={GET, POST})
    public String remove(HttpServletRequest request,
                         @PathVariable int stuNum){
        System.out.printf("/remove/ %s ~\n",request.getMethod());
        System.out.println("stuNum = " + stuNum);
        repository.delete(stuNum);
        return "redirect:/score/list";
    }
    @GetMapping("/detail")
    public String detail(int stuNum, Model model){
        System.out.println("/detail/ GET~");

        Score score = repository.findOne(stuNum);
        model.addAttribute("s",score);
        return "chap04/score-detail";
    }

    // 5. 수정 입력 폼을 열어주는 요청
    // /score/modify : GET
    @GetMapping("modify")
    public String modify(int stuNum, Model model){
        System.out.println("modify GEt~!! ");

        Score score = repository.findOne(stuNum);
        model.addAttribute("s",score);

        return "chap04/score-modify";
    }
    //6. 수정 처리 요청
    // /score/modify : POST
    @PostMapping("modify")
    public String modify(int stuNum, ScoreRequestDTO dto){
        System.out.println("modify POST~!! ");
        //수정 흐름
        //클라이언트가 수정할 데이터를 보냄
        // -> 서버에 저장되어 있는 기존 데이터를 조회해 수정한다.
        Score score = repository.findOne(stuNum);
        score.changeScore(dto);
        return "redirect:/score/detail?stuNum="+stuNum;
    }

}
