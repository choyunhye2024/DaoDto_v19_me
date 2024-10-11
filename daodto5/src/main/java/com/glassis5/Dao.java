package com.glassis5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Dao {

	// 데이터베이스 연결을 위한 Connection 객체
	public Connection con = null;
	// SQL구문을 실행하기 위한 Statement 객체
	public Statement st = null;

	// 데이터베이스 연결을 위한 메서드
	public void connect() {
		try {

			// 고정 1: jdbc드라이버 클래스를 로드
			Class.forName(Db.DB_JDBC_DRIVER_PACKAGE_PATH);

			// 고정 2: DB에 연결
			// Db 클래스에서 url, 사용자 id, pw를 불러와서 연결
			con = DriverManager.getConnection(Db.DB_URL, Db.DB_ID, Db.DB_PW);

			// 고정 3: Statement 객체 생성 (SQL구문 실행에 사용)
			st = con.createStatement();
		} catch (Exception e) {
			// 예외(오류)발생시 스택 출력
			e.printStackTrace();
		}
	}

	// SQL 업데이트 (insert, update, delete)를 실행하는 메서드
	public void update(String sql) {
		try {
			// 전달받은 SQL 구문을 실행
			st.executeUpdate(sql);
		} catch (Exception e) {
			// 예외 발생시 스택 추적을 출력
			e.printStackTrace();
		}
	}

	// 데이터베이스 연결을 종료하는 메서드
	public void close() {
		try {
			// 고정 4: Statement 객체 종료
			st.close();
			// 고정 5: connection 객체 종료 (DB연결해제)
			con.close();
		} catch (Exception e) {
			// 예외 발생시 스택 추적을 출력
			e.printStackTrace();
		}
	}

}
