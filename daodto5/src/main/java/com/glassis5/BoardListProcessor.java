package com.glassis5;

import java.util.ArrayList;

public class BoardListProcessor {

	private static final int PAGE_LINK_AMOUNT = 0;
	private DaoBoard dao;
	public ArrayList<Dto> posts;
	// 전체 페이지 수 (페이징)
	public int totalPage = 0;
	// 현재 페이지 번호
	public int currentPage = 0;

	// (1/9) 블럭 총 갯수 구하기
	int totalBlock = 0;
	// (2/9) 현재 블럭번호 구하기
	int currentBlockNo = 0;
	// (3/9) 블럭 시작 페이지번호 구하기
	int blockStartNo = 0;
	// (4/9) 블럭 페이지 끝번호 구하기
	int blockEndNo = 0;
	// (5/9) (이전 / 다음) 관련 초기화 처리
	int prevPage = 0;
	int nextPage = 0;
	// (6/9) (이전 / 다음) 관련 계산 등 처리
	boolean hasPrev = true; // 이전 블럭가기 가능 여부 저장값 초기화
	boolean hasNext = true; // 다음 블럭가기 가능 여부 저장값 초기화

	public BoardListProcessor(DaoBoard dao, String currentPage) {
		super();
		this.dao = dao;
		this.currentPage = Integer.parseInt(currentPage);
		this.totalPage = getPageCount();
		getList(); // 현재 페이지 번호와 전체 페이지 수를 기반으로 리스트 데이터얻어오기

		// (1/9) 블럭 총 갯수 구하기
		// 블럭 총 갯수 = 페이지 수 / 블럭당 페이지 수 (결과에 올림처리)
		// Math. 함수들 : 반올림(round), 올림 (ceil), 버림(floor)
		totalBlock = (int) Math.ceil((double) totalPage / Board.PAGE_LINK_AMOUNT);

		// (2/9) 현재 블럭번호 구하기
		// 현재 블럭번호 = 현재 페이지번호 / 블럭당 페이지 수 (결과에 올림처리)
		currentBlockNo = (int) Math.ceil((double) this.currentPage / Board.PAGE_LINK_AMOUNT);

		// (3/9) 블럭페이지 번호 구하기
		// 블럭 시작 페이지 번호 = (현재 블럭번호 - 1) * 블럭당 페이지 수 + 1
		blockStartNo = (currentBlockNo - 1) * Board.PAGE_LINK_AMOUNT + 1;

		// (4/9) 블럭페이지 끝번호 구하기
		// 블럭페이지 끝 번호 = 현재 블럭번호 * 블럭 당 페이지 수
		blockEndNo = currentBlockNo * Board.PAGE_LINK_AMOUNT;
		if (blockEndNo > totalPage) { // 블럭 마지막 페이지 번호가 젅체 페이지 마지막 번호보다 큰 경우에
			blockEndNo = totalPage; // 블럭 마지막 페이지 번호를 페이지 마지막 번호로 저장하는 예외처리

			// (6/9) (이전 / 다음) 관련 계산처리
			// 현재 블럭에서 이전/다음이 가능한지 계산하고 가능 여부를 저장해두기
			if (currentBlockNo == 1) { // 현재 블럭이 1번 블럭이면
				hasPrev = false; // 이전 블럭 가기 불가능
			} else { // 현재 블럭이 1번 블럭이 아니면
				hasPrev = true; // 이전블럭가기 가능
				// 이전 블럭 이동 시 몇페이지로 이동할지 정하기
				// (현재 블럭번호 - 1)*블럭 당 페이지 수
				prevPage = (currentBlockNo - 1) * Board.PAGE_LINK_AMOUNT;
			}

			if (currentBlockNo < totalBlock) { // 현재 블럭이 마지막 블럭보다 작으면
				hasNext = true; // 다음블럭가기 가능
				// 다음 블럭 이동시 몇 페이지로 이동할지 정하기
				// 다음 블럭의 첫 페이지로 이동하게 하면됨
				// 공식 : 현재 블럭번호 * 블럭당 페이지수 + 1
				nextPage = currentBlockNo * Board.PAGE_LINK_AMOUNT + 1;
			} else { // 현재 블럭이 마지막 블럭보다 같거나 크면
				hasNext = false; // 다음블럭가기 불가능
			}
		}
	}

	public void getList() {

		int startIndex = (currentPage - 1) * Board.LIST_AMOUNT; // 시작인덱스 계산해서 넘기기
		posts = dao.selectList(startIndex);

	}

	// 총페이지 수 구하기
	public int getPageCount() {

		int totalPageCount = 0;
		int count = dao.selectPostCount();
		if (count % Board.LIST_AMOUNT == 0) { // case 1. 나머지없이 딱 떨어지는 경우
			totalPageCount = count / Board.LIST_AMOUNT;
		} else { // case 2. 나머지가 있어서 짜투리 페이지가 필요한 경우
			totalPageCount = count / Board.LIST_AMOUNT + 1;
		}
		return totalPageCount;
	}

	// 총 페이지 수 구하기 (검색)
	public int getSearchPageCount(String word) {
		int totalPageCount = 0;
		int count = dao.selectSearchPostCount(word);
		if (count % Board.LIST_AMOUNT == 0) { // 나머지가 없이 딱 떨어지는 경우
			totalPageCount = count / Board.LIST_AMOUNT;
		} else { // 나머지가 있어서 짜투리 페이지가 필요한 경우
			totalPageCount = count / Board.LIST_AMOUNT + 1;
		}
		return totalPageCount;
	}

	// 글 객체를 얻는 함수
	public ArrayList<Dto> getPosts() {
		return posts;
	}

	// 페이지 리스트들을 출력하기 위한 html을 리턴
	public String getHtmlPageList() {
		String html = "";

		// (7/9) (이전/다음) 의 (이전) 처리
		// 이전 블럭 이동이 가능하면 미리 계산한 이전 블럭 이동 시 이동할 페이지 번호를 링크에 전달
		if (hasPrev) {
			html = html + String.format("<a href = 'board/list?page=%d'>이전 블럭가기</a>", prevPage);
		}
		// (8/9) 기존에 제한없던 페이지 리스트 나열을 블럭적용하기
		// 현재 블럭 페이지 시작번호와 끝번호를 이용하여 반복문의 시작값 끝값으로 하고 이 값을 페이지 번호로 출력하기
		for (int i = blockStartNo; i <= blockEndNo; i++) {
			html = html + String.format("<a href =;'/board/list?page=%d'>%d</a>&nbsp; &npsp;", i, i);
		}
		// (9/9) (이전 / 다음)의 (다음)처리
		// 다음 블럭 이동이 가능하면 미리 계산 한 블럭 이동시 이동할 페이지번호를 링크에 전달하기
		if (hasNext) {
			html = html + String.format("<a href = '/board/list?page=%d'>다음블럭가기</a>", nextPage);
		}
		return html;
	}

}
