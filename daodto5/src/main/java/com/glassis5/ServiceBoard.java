package com.glassis5;

public class ServiceBoard {

	DaoBoard dao; // DaoBoard 객체 선언

	public ServiceBoard() {
		// ServiceBoard 객체가 생성될때 DaoBoard 객체를 생성
		dao = new DaoBoard();
	}

	// 게시글 삭제 서비스 메서드
	public void del(String no) {
		dao.del(no); // 게시글 번호(no)를 받아 dao의 삭제 메서드 호출
	}

	public void write(Dto d) {
		dao.insert(d); // 게시글 데이터를 담은 dto 객체를 받아 dao의 삽입 메서드 호출
	}

	// 게시글 읽기 메서드
	public Dto read(String no) {
		// 게시글 번호(no)를 받아 dao의 게시글 조회 메서드를 호출하고 결과 반환
		return dao.selectPost(no);
	}

	// 게시글 리스트 처리 서비스 메서드
	public BoardListProcessor list(String currentPage) {
		// 현재 페이지 정보가 없으면 기본값을 1로 설정
		if (currentPage == null) {
			currentPage = "1";
		}
		// DaoBoard 객체와 현재 페이지 정보를 기반으로 BoardListProcessor 객체생성
		BoardListProcessor blp = new BoardListProcessor(dao, currentPage);
		return blp; // 생성된 BoardListProcessor 객체를 반환(목록 처리결과)
	}

	// 게시글 수정 서비스 메서드
	public void edit(Dto d, String no) {
		// 수정된 게시글 정보를 담은 Dto 객체와 게시글 번호를 받아 Dao 수정 메서드 호출
		dao.update(d, no);
	}

}
