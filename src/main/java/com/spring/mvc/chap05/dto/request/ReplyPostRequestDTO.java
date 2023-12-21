package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyPostRequestDTO {

    //NotBlank : null 안돼, 빈문자열도 안돼
    //NotNull : null만 안돼
    @NotBlank
    @Size(min = 1,max=300)
    private String text; //댓글 내용
    @NotBlank
    @Size(min = 2,max=8)
    private String author; //댓글 작성자
    @NotNull
    private long bno; //원본 글 번호

    //dto를 entity로 바꾸는 변환 메서드
    public Reply toEntity(){
        return Reply.builder()
                .replyText(this.text)
                .replyWriter(this.author)
                .boardNo(this.bno)
                .build();

    }
}
