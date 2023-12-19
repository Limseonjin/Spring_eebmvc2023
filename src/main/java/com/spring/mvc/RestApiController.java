package com.spring.mvc;

import com.spring.mvc.chap06.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@ResponseBody //클라이언트에게 jsp를 보내는게 아니라 json을 보내는 방법
@RestController //controller + responsebody
@RequestMapping("/rests")
@Slf4j

public class RestApiController {

    @GetMapping("/hello")
    public String hello(){
        log.info("/rest/hello Get");
        return "hello apple banana!";
    }

    @GetMapping("/food")
    public List<String> food(){
        return List.of("짜장면","볶음밥","탕슈육");
    }
    @GetMapping("/person")
    public Person person() {
        return new Person("334", "말포위", 50);
    }

    /*
    * RestController에서 리턴타입을 ResponseEntity를 쓰는 이유
    * -클라이언트에게 응답할 때는 메세지 바디 안에 있는 데이터도 중요하지만
    *  - 상태코드와 헤더정볼르 포함해야하ㅣ
    * - 근데 일번 리턴타입은 상태코드와 헤더 전송하기 어렵다.
    *
    *
    * */

    @GetMapping("/person-list")
    public ResponseEntity<?> personList(){
        Person p1 = new Person("111", "딸기경듀", 30);
        Person p2 = new Person("222", "잔먕류피", 40);
        Person p3 = new Person("333", "뽀로로롱", 20);

        List<Person> personList = List.of(p1, p2, p3);

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("my-pet","냥냥이");
        return ResponseEntity
                .ok()
//                .headers(headers)
                .body(personList);
    }
    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(
            @RequestParam(required = false) Double cm,
            @RequestParam(required = false) Double kg){
        if (cm == null || kg == null){
            return ResponseEntity.badRequest().body("키랑 몸무게를 필수로 전달!");
        }
        Double bmi = kg/ ((cm/100) * (cm/100));

        HttpHeaders headers = new HttpHeaders();
        headers.add("my-pet","hi");

        return ResponseEntity.ok().headers(headers).body(bmi);
    }
}
