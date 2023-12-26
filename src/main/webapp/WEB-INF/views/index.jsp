<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
    <%@ include file="include/static-header.jsp"%>
    <style>
        h1 {
            margin: 200px auto;
            width: 40%;
            font-size: 40px;
            font-weight: 700;
            color: orange;
            text-align: center;
        }
    </style>

</head>
<body>
    <%
        String userName = "방문자";
        // 클라이언트에게 쿠키를 검사
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("login")){
                userName = c.getValue();
            }
        }
    %>

    <%@ include file="include/header.jsp"%>
<%--    <h1><%= userName %>님 하이하이요!</h1>--%>
<%--
        서버에서 Model이나 RedirectAttributes에 담아 놓은 데이터는
        ${requestScope.aa} <-로 참조 가능 requestScope를 생략가능

        세션에 담은 데이터는
        ${sessionScope.aaa}로 참조가능.
        Model에 같은 이름없으면 sessionScope 생략 가능
--%>
    <c:if test="${login == null}">
        <h1>방문자님 안뇽하내용</h1>
    </c:if>
    <c:if test="${login != null}">
        <h1>${login.nickName}(${login.account})님 어서오세욘</h1>
    </c:if>


</body>
</html>