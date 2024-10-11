package com.glassis5;

public class Cw {

	// 출력할 기호
	private static final String DOT = "★";
	// 선을 그릴때 사용할 길이
	private static final int LINE_LENGTH = 32;

	// 문자열을 콘솔에 출력하는 메서드
	public static void w(String s) {
		System.out.println(s); // 줄바꿈 없이 출력
	}

	// 문자열을 출력하고 줄바꿈을 하는 메서드
	public static void wn(String s) {
		System.out.println(s); // 출력 후 줄바꿈
	}

	// 단순히 줄바꿈을 하는 메서드 (오버로딩된 메서드)
	public static void wn() {
		System.out.println(); // 아무내용없이 줄바꿈
	}

	// 지정된 기호(DOT)를 이용해 LINE_LENGTH 만큼 선을 그리는 메서드
	public static void line() {
		for (int i = 0; i < LINE_LENGTH; i++) { // LINE_LENGTH 만큼 반복
			w(DOT); // 기호를 출력
		}
		wn(); // 선을 다 그린 후 줄바꿈
	}

	// 단일 기호(DOT)를 출력하는 메서드
	public static void dot() {
		w(DOT); // 기호 하나 출력
	}

	// 지정된 숫자만큼 공백을 출력하는 메서드
	public static void space(int c) {
		for (int i = 0; i < c; i++) { // C횟수만큼 반복
			w(" "); // 공백을 출력
		}
	}
}
