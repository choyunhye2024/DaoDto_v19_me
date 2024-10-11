package com.glassis5;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoBoard extends Dao {

	// (1/5) 글 삭제
	public void del(String no) { // 해당 게시물의 번호를 불러와야해서 no(number)을 정의한다.
		connect(); // DB연결(고정1,2,3) = connect or super.connect
		// 글 번호에 해당하는 게시글을 삭제하는 SQL 쿼리
		String sql = String.format("delete from %s where b_no=%s", Board.TABLE_PS_BOARD_FREE, no);
		super.update(sql); // SQL실행
		super.close(); // DB연결 종료 (고정 4,5)
	}

	// (2/5) 글 작성
	public void insert(Dto d) {
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		String sql = String.format("insert into %s (b_title, b_id, b_text) value ('%s','%s','%s')",
				Board.TABLE_PS_BOARD_FREE, d.title, d.id, d.text);
		super.update(sql); // SQL실행
		super.close(); // DB연결종료 (고정 4,5)
	}

	// (3/5) 글 조회
	public Dto selectPost(String no) { // 조회할 특정 no(number)을 불러와야함
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		Dto post = null; // 조회한 게시글 정보를 저장할 객체
		try {
			// 글 번호에 해당하는 게시물 정보를 조회하는 SQL 쿼리
			String sql = String.format("select * from %s where b_no=%s", Board.TABLE_PS_BOARD_FREE, no);
			ResultSet rs = st.executeQuery(sql); // SQL실행 후 결과 가져오기
			rs.next(); // 결과의 첫번째 행으로 이동
			// 조회한 게시글 데이터를 Dto 객체로 생성
			post = new Dto(rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"),
					rs.getString("B_DATETIME"), rs.getString("B_HIT"), rs.getString("B_TEXT"),
					rs.getString("B_REPLY_COUNT"), rs.getString("B_REPLY_ORI"));
		} catch (Exception e) {
			e.printStackTrace(); // 에러발생 시 출력
		}
		super.close();
		return post;

	}

	// (4/5-1) 글 리스트 조회 (백업용)
	public ArrayList<Dto> selectListBackup(String page) {
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		ArrayList<Dto> posts = new ArrayList<>(); // 조회한 게시물 목록을 저장할 리스트
		try {
			int startIndex = ((Integer.parseInt(page)) - 1) * Board.LIST_AMOUNT; // 페이징처리 시작 인덱스계산
			// 게시글 목록을 조회하는 SQL 쿼리 (페이징처리)
			String sql = String.format("select * from %s limit %s, %s", Board.TABLE_PS_BOARD_FREE, startIndex,
					Board.LIST_AMOUNT);
			ResultSet rs = st.executeQuery(sql); // SQL실행 후 결과 가져오기
			while (rs.next()) {
				// 각 게시글 데이터를 Dto객체로 생성하여 리스트에 추가
				posts.add(new Dto(rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"),
						rs.getString("B_DATETIME"), rs.getString("B_HIT"), rs.getString("B_TEXT"),
						rs.getString("B_REPLY_COUNT"), rs.getString("B_REPLY_ORI")));
			}
		} catch (Exception e) {
			e.printStackTrace(); // 에러발생 시 출력
		}
		super.close(); // DB연결종료 (고정 4,5)
		return posts; // 게시글 목록 반환
	}

	// (4/5-2) 글 리스트 조회 (현재 사용중)
	public ArrayList<Dto> selectList(int startIndex) {
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		ArrayList<Dto> posts = new ArrayList<>();
		try {
			// 게시물 목록을 조회하는 SQL 쿼리 (페이징처리)
			String sql = String.format("select * from %s limit %d, %d", Board.TABLE_PS_BOARD_FREE, startIndex,
					Board.LIST_AMOUNT);
			ResultSet rs = st.executeQuery(sql); // SQL실행 후 결과 가져오기
			while (rs.next()) {
				// 각 게시글 데이터를 Dto 객체로 생성하여 결과 가져오기
				posts.add(new Dto(rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"),
						rs.getString("B_DATETIME"), rs.getString("B_HIT"), rs.getString("B_TEXT"),
						rs.getString("B_REPLY_COUNT"), rs.getString("B_REPLY_ORI")));
			}
		} catch (Exception e) {
			e.printStackTrace(); // 에러발생 시 출력
		}
		super.close(); // DB연결 종료 (고정 4,5)
		return posts; // 게시글 목록 반환
	}

	// (5/5) 글 수정
	public void update(Dto d, String no) {
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		// 글 번호에 해당하는 게시글의 제목과 내용을 수정하는 SQL쿼리
		String sql = String.format("update %s set b_title='%s', b_text='%s' where b_no=%s", Board.TABLE_PS_BOARD_FREE,
				d.title, d.text, no); // (title:제목), (text:내용) = 위 주석설명 참고
		super.update(sql); // SQL실행
		super.close(); // 데이터베이스 연결종료 (고정 4,5)
	}

	// 총 글 수 구하기
	public int selectPostCount() {
		int count = 0; // 총 글 수
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		try {
			// 전체 글 수를 구하는 SQL쿼리
			String sql = String.format("select count(*) from %s", Board.TABLE_PS_BOARD_FREE);
			ResultSet rs = st.executeQuery(sql);// SQL 실행 후 결과 가져오기
			count = rs.getInt("count(*)"); // 게시글 수 가져오기
		} catch (Exception e) {
			e.printStackTrace(); // 에러발생 시 출력
		}
		super.close(); // DB연결종료 (고정 4,5)
		return count; // 게시글 수 반환
	}

	// 검색 결과의 총 글 수 구하기
	public int selectSearchPostCount(String word) {
		int count = 0;
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		try {
			String sql = String.format("select count(*) from %S where b_title like '%%%s%%'", Board.TABLE_PS_BOARD_FREE,
					word);
			ResultSet rs = st.executeQuery(sql); // SQL 실행 후 결과 가져오기
			rs.next(); // 결과의 첫 행으로 이동
			count = rs.getInt("count(*)"); /// 게시글 수 가져오기
		} catch (Exception e) {
			e.printStackTrace(); // 에러시 출력하기
		}
		super.close(); // DB연결종료 (고정 4,5)
		return count; // 게시글 수 반환
	}

	// 검색결과 리스트 조회
	public ArrayList<Dto> selectlistSearch(String word, String page) {
		connect(); // DB연결 (고정 1,2,3) = connect or super.connect
		ArrayList<Dto> posts = new ArrayList<>(); // 조회산 게시글 목록 저장할 리스트
		try {
			int startIndex = ((Integer.parseInt(page)) - 1) * Board.LIST_AMOUNT; // 페이징 처리 시작 인덱스
			// 제목에 검색어가 포함된 게시글 목록을 조회하는 SQL 쿼리 (페이징처리)
			String sql = String.format("select * from where b_title like '%%%s%%' limit %s %s",
					Board.TABLE_PS_BOARD_FREE, word, startIndex, Board.LIST_AMOUNT);
			ResultSet rs = st.executeQuery(sql); // SQL실행 후 결과 가져오기
			while (rs.next()) {
				// 각 게시글 데이터를 Dto객체로 생성하여 리스트에 추가하기
				posts.add(new Dto(rs.getString("B_NO"), rs.getString("B_TITLE"), rs.getString("B_ID"),
						rs.getString("B_DATETIME"), rs.getString("B_HIT"), rs.getString("B_TEXT"),
						rs.getString("B_REPLY_COUNT"), rs.getString("B_REPLY_ORI")));
			}
		} catch (Exception e) {
			e.printStackTrace(); // 에러발생 시 출력
		}
		super.close(); // 에러 발생 시 출력
		return posts; // 검색 결과 게시글 목록반환
	}

}
