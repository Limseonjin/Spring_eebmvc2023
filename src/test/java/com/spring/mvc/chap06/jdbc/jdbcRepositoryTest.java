package com.spring.mvc.chap06.jdbc;

import com.spring.mvc.chap06.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class jdbcRepositoryTest{
    @Autowired
    JdbcRepository repository;

    @Test
    @DisplayName("DB접속에 성공해야 한다")
    void connectTest(){
        try {
            Connection connection = repository.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("사람 객체정보를 DB에 삽입해야한다.")
    void saveTest(){
        Person p = new Person("1", "랑이", 2);
        repository.save(p);
    }

    @Test
    @DisplayName("번호가 1인 사람 이름,나이를 수정한다")
    void updateTest(){
        //given
        String id = "1";
        String newName = "둉딘";
        int newAge = 22;

        //when
        Person p2 = new Person(id, newName, newAge);
        repository.update(p2);
    }
    @Test
    @DisplayName("번호가 1인 사람을 삭제해야한다")
    void deleteTest() {
        //given
        String id = "1";

        //when
        repository.delete(id);
    }

    @Test
    @DisplayName("랜덤 회원 아이디를 가진 회원을 10명 등록해야한다.")
    void bulkInsertTest(){
        for (int i = 0; i < 10; i++) {
            Person p = new Person(""+Math.random(),"랄랄라"+i, i+10);
            repository.save(p);
        }
    }

    @Test
    @DisplayName("전체화면을 조회하면 회원 리스트 수가10개이다")
    void findAllTest(){
        List<Person> pe = repository.findAll();
        pe.forEach(System.out::println);
    }
    @Test
    @DisplayName("전체화면을 조회하면 회원 리스트 수가10개이다")
    void findOneTest(){
        String id = "0.5563591385574401";
        Person one = repository.findOne(id);
        System.out.println("one = " + one);
    }
}