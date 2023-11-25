package com.spring.mvc.chap04.entity;


import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import lombok.*;

/*
* 엔터티 클래스
*  - 데이터 베이스에 저장할 데이터를 자바 클래스에 매칭
* * */
@Setter@Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private String name; // 이름
    private int kor, eng, math; //국영수 점수

    private int stuNum; // 학번
    private int total; // 총점
    private double average; // 평균
    private Grade grade; //학점

    public Score(ScoreRequestDTO score) {
        convertInputData(score);

        totalAndAverage();
        GradeSelect();
    }

    private void GradeSelect() {
        if (average>=90) this.grade = Grade.A;
        else if (average>=80) this.grade = Grade.B;
        else if (average>=70) this.grade = Grade.C;
        else if (average>=60) this.grade = Grade.D;
        else this.grade = Grade.F;
    }

    private void totalAndAverage() {
        this.total = kor+eng+math;
        this.average = total / 3.0;
    }

    private void convertInputData(ScoreRequestDTO score) {
        this.name = score.getName();
        this.kor = score.getKor();
        this.eng = score.getEng();
        this.math = score.getMath();
    }

    public void changeScore(ScoreRequestDTO dto) {
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
        totalAndAverage();
        GradeSelect();
    }
}
