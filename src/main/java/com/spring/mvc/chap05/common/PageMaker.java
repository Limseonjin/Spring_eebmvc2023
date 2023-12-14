package com.spring.mvc.chap05.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
public class PageMaker {
    //페이지 첫번호와 끝번호, 보정된 end
    private int begin, end, finalPage;

    //이전, 다음 버튼 활성화 여부
    private boolean prev,next;

//    현재 페이지 정보
    private Page page;

    //총 게시물 수
    private int totalCount;

    public PageMaker(Page page, int totalCount){
        this.page = page;
        this.totalCount = totalCount;

        makePageInfo();
    }
    //한 화면에 보여질 페이지 수
    public static final int PAGE_COUNT = 10;

    private void makePageInfo(){
        //1.end값 계산
        /*
            지금 사용자가 7페이지에 있다 -> 1~10구간을 만들어야함
            지금 사용자가 24페이지에 있다 -> 21~30구간을 만들어야함

            //5개씩 쪼개는 경우
            현재 13페이지 11~15
            현제 38페이지 36~40
        * */
        // 공식 :(올림(현재 위치한 페이지넘버 / 한 화면에 보여줄 페이지 수 )) * 한 화면에 보여줄 페이지 수

        this.end = (int) ((Math.ceil((double) page.getPageNo() /PAGE_COUNT))*PAGE_COUNT);

        // 2. begin 구하기
        this.begin = this.end - PAGE_COUNT + 1;

        // 3. prev활성화 여부
        /*
        *   1~10구간 : prev 비활성화
        *   11~20 : prev 활성화
        *   21~30 : prev활성화
        * */
        this.prev = begin > 1;

        /*
        *   end 값 보정
        *   총 게시물 237개이고 한 화면에 게시물을 10개씩 배치하고 있다면
        *   1~10페이지 구간 : 게시물 총 100개
        *   11~20페이지 구간 : 게시물 총 100개
        *   21~30페이지 구간 : 게시물 총 37개
        *
        *   -마지막 구간 보정 공식
        *   올림 ( 총 게시물 수 / 한 페이지에 배치할 게시물 수 )
        *
        * */
        this.finalPage = (int) Math.ceil((double) totalCount / page.getAmount());

        //마지막 페이지 구간에서 end값을 finalPage 값으로 변경
        if (this.finalPage < this.end)
            this.end = this.finalPage;
        // 4. next 활성화 여부
        this.next = this.end < this.finalPage;
    }
}
