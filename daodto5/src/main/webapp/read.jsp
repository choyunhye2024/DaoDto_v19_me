<%@page import="com.glassis5.Dto"%>
<%@page import="com.glassis5.DaoBoard"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Dto d=(Dto)request.getAttribute("post");
%>

<%=d.no%>
<%=d.id%>
<%=d.title%>
<hr>
<%=d.text%>

<a href="/board/del?no=<%=d.no%>">삭제</a>
<a href="/board/edit_insert?no=<%=d.no%>">수정</a>
<a href="/board/list">리스트로</a>

</body>
</html>