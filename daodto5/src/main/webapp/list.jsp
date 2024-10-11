<%@page import="com.glassis5.Dto"%>
<%@page import="com.glassis5.BoardListProcessor"%>
<%@page import="java.util.ArrayList"%> 

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title> <!-- 페이지 제목 설정 -->
</head>
<body>
글번호, 제목, 작성자<hr> <!-- 게시글 리스트 출력 제목 -->

<%
/* 서버에서 전달된 게시판 목록 처리 객체를 가져옴 */
BoardListProcessor blp = (BoardListProcessor)request.getAttribute("blp"); 

/* BoardListProcessor로부터 게시글 목록을 ArrayList 형태로 가져옴 */
ArrayList<Dto> posts = blp.getPosts();
%>

<!-- 게시글 리스트 출력 시작 -->
<%
/* 게시글 목록을 반복문으로 출력 */
for(int i=0;i<posts.size();i=i+1){
%>

<!-- 게시글 번호 출력 -->
<%=posts.get(i).no%> 

<!-- 게시글 제목을 링크로 출력 (글 번호를 통해 상세 페이지로 이동) -->
<a href="/board/read?no=<%=posts.get(i).no%>"><%=posts.get(i).title%></a> 

<!-- 게시글 작성자 출력 -->
<%=posts.get(i).id%>
<hr>
<%
}
%>

<!-- 게시판 페이지 네비게이션 -->

<%=blp.getHtmlPageList()%> <!-- 페이지 목록을 HTML로 출력 (페이지 이동 버튼) -->


<!-- 글쓰기 버튼 -->
<a href="/write.jsp">쓰기</a> <!-- 글쓰기 페이지로 이동 -->

<!-- 목록 페이지로 돌아가는 버튼 -->
<a href="/board/list">list로</a> <!-- 게시판 목록 페이지로 이동 -->

<!-- 홈 페이지로 이동하는 버튼 -->
<a href="/">홈으로</a> <!-- 홈페이지로 이동 -->

</body>
</html>
