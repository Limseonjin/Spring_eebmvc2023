<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
</head>
<body>
    <c:if test="${isLogin}">
        <h1>로그인 성공!!</h1>
    </c:if>
    <c:if test="${!isId  && isPw}">
        <h1>아이디가 존재하지 않습니다</h1>
    </c:if>
    <c:if test="${isId && !isPw}">
        <h1>패스워드가 틀립니다</h1>
    </c:if>
    <c:if test="${!isId && !isPw}">
        <h1>로그인 실패 ㅠㅠ </h1>
    </c:if>
    <a href="/hw/s-login-form">다시 로그인하기</a>
</body>
</html>