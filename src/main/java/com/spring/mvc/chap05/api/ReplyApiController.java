package com.spring.mvc.chap05.api;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.dto.request.ReplyModifyRequestDTO;
import com.spring.mvc.chap05.dto.request.ReplyPostRequestDTO;
import com.spring.mvc.chap05.dto.response.ReplyDetailResponseDTO;
import com.spring.mvc.chap05.dto.response.ReplyListResponseDTO;
import com.spring.mvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
/*
    REST API URL 설계 원칙
    - CRUD는 URL에 명시하는게 아니라 HTTP method로만 표현해야 함!
    => /replies/write (x)
    => replies :POST (O)

    => /replies/all (X)     -전체조회
    => /replies   : GET (O) -전체조회
    => /replies/17 : GET    -단일 조회

    => /replies/delete?replyNo=3 (X)
    =? /replies/3    : DELETE (O)
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/replies")
public class ReplyApiController {
    private final ReplyService replyService;

    //댓글 목록 조회 요청
    //URL : api/v1/replies/글번호/page/페이지번호
    @GetMapping("/{boardNo}/page/{pageNo}")
    public ResponseEntity<?> list(
            @PathVariable long boardNo,
            @PathVariable int pageNo
    ){
        log.info("/api/v1/replies/{}/page/{} : GET!!",boardNo,pageNo);
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setAmount(3);

        ReplyListResponseDTO replies = replyService.getList(boardNo, page);
        return ResponseEntity.ok().body(replies);
    }

    //댓글 등록 요청 처리
    //@RequestParam <- 기본값, 동기요청에서 ? 붙은 파라미터를 읽음
    // @RequestBody : 비동기 요청에서 메세지 바디 안에 있는 JSON을 파싱
    //Validated : 태그가 붙은 것을 검증함
    @PostMapping
    public ResponseEntity<?> crete(
            @Validated @RequestBody ReplyPostRequestDTO dto,
                                   HttpSession session,
                                   BindingResult result){ //검증 결과 메세지를 가진 객체
        //d입력값 검증에 걸리면 400번 코드와 함꼐 메세지를 클라이언트에 전송
        if (result.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }
        log.info("api/v1/replies : POST");
        log.debug("request parameter : {}",dto);

        try {
            ReplyListResponseDTO responseDTO = replyService.register(dto,session);
            return ResponseEntity.ok().body(responseDTO);
        } catch (SQLException e) {
            log.warn("500 status coid response! cause by: {}",e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //댓글삭제 요청 처리
    @DeleteMapping("/{replyNo}")
    public ResponseEntity<?> remove(
            @PathVariable Long replyNo){
        if (replyNo == null){
            return ResponseEntity
                    .badRequest()
                    .body("댓글번호를 보내주셔유");
        }

        log.info("/api/v1/replies/{} : DELETE",replyNo);
        try {
            ReplyListResponseDTO responseDTO = replyService.delete(replyNo);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        }catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    //댓글 수정 요청 처리
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> update(
            @Validated @RequestBody ReplyModifyRequestDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }
        log.info("/api/v1/replies PUT/PATCH");
        log.debug("parameter : {}",dto);

        try{
            ReplyListResponseDTO responseDTO = replyService.modify(dto);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        }
        catch (Exception e){
            log.warn("internal server error cause by:{}",e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }
}
