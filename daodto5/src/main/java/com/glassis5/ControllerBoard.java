package com.glassis5;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerBoard
 */
@WebServlet("/board/*")
public class ControllerBoard extends HttpServlet {
	String nextPage; // 다음페이지를 저장할 변수
	ServiceBoard service; // 게시판 서비스 객

	@Override
	public void init() throws ServletException {
		// 서블릿이 초기화 될 때 서비스 객체 생성
		service = new ServiceBoard();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// http get요청을 처리
		String action = request.getPathInfo(); // 요청 url에서 서블릿이후의 경로를 가져옴
		Cw.wn("action:" + action); // 로그출력
		if (action != null) {
			// 경로에 따라 처리할 작업을 결정하는 switch문
			switch (action) {
			case "/del": // 글 삭제
				nextPage = "/board/list"; // 삭제 후 게시글 리스트 페이지로 이동
				service.del(request.getParameter("no"));// 서비스: 삭제서비스 호출, 게시글 번호로 삭제
				break;

			case "/write": // 글 작성
				nextPage = "/board/list"; // 작성 후 게시글 리스트페이지로 이동
				// 게시글 데이터를 Dto 객체로 생성하여 전달
				Dto dto = new Dto(request.getParameter("title"), request.getParameter("id"),
						request.getParameter("text"));
				service.write(dto); // 서비스 : 글쓰기 서비스 호출
				break;

			case "/edit_insert": // 수정할 글 불러오기
				Cw.wn("수정-insert"); // 로그출력
				nextPage = "/edit.jsp"; // 수정할 글을 불러와서 수정페이지로 이동
				// 수정할 게시글 번호를 받아 서비스에서 해당 글을 가져옴
				// 서비스: 읽기 서비스 호출
				request.setAttribute("post", service.read(request.getParameter("no")));
				break;

			case "/edit_proc": // 글 수정 처리
				Cw.wn("수정-proc"); // 로그출력
				nextPage = "/board/list"; // 수정 후 게시글 리스트 페이지로 이동
				// 수정된 제목과 내용으로 Dto 객체 생성 후 수정 서비스 호출
				service.edit(new Dto(request.getParameter("title"), request.getParameter("text")),
						request.getParameter("no")); // 서비스: 수정 서비스 호출

			case "/read": // 게시글 읽기
				nextPage = "/read.jsp"; // 읽기 후 게시글 상세 페이지로 이동
				// 서비스 : 읽기 서비스 호출, 게시글 번호남기기
				Dto d = service.read(request.getParameter("no"));
				request.setAttribute("post", d);// 읽어온 게시글을 request에 저장
				break;

			case "/list": // 게시글 리스트
				nextPage = "/list.jsp"; // 리스트 페이지로 이동
				// 현재 페이지 정보를 받아 서비스에 게시글 목록 처리
				BoardListProcessor blp = service.list(request.getParameter("page"));
				request.setAttribute("blp", blp); // 리스트 처리결과를 request에 저장
			}
			// 다음페이지로 요청을 전달하여 포워딩
			RequestDispatcher d = request.getRequestDispatcher(nextPage);
			d.forward(request, response);

		}
	}

}
