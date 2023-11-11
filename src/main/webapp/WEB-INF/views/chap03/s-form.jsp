<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
</head>
<body>
    <h1>로그인 하십쇼!!!</h1>
    <p>grape111 / ggg9999</p>
    <form action="/hw/s-login-check" method="POST">
        로그인 : <input type="text" name="userID"> <br>
        비밀번호 : <input type="text" name="userPW"> <br>
        <br>
        <button type="submit">확인</button>
    </form>
</body>
</html>