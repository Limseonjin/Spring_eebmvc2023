package com.spring.mvc.chap05.dto.response;

import com.spring.mvc.chap05.common.PageMaker;
import lombok.*;

import java.util.List;

@Setter@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyListResponseDTO {

    private List<ReplyDetailResponseDTO> replies;
    private PageMaker pageInfo; //페이징 정보
    private int count; //충 댓글 순


}