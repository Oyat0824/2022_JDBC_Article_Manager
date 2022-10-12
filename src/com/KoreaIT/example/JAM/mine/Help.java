package com.KoreaIT.example.JAM.mine;

public class Help {
	public static void showHelp() {
		System.out.println(CT.F_PURPLE + "=== 게시글 전용 명령어 ===" + CT.RESET);
		System.out.println(CT.F_GREEN + "article write			: 게시글 작성" + CT.RESET);
		System.out.println(CT.F_YELLOW + "article detail [번호]		: 게시글 열람" + CT.RESET);
		System.out.println(CT.F_GREEN + "article modify [번호]		: 게시글 수정" + CT.RESET);
		System.out.println(CT.F_YELLOW + "article delete [번호]		: 게시글 삭제" + CT.RESET);
		System.out.println(CT.F_GREEN + "article list			: 게시글 목록" + CT.RESET);

		System.out.println(CT.F_PURPLE + "\n=== 회원 전용 명령어 ===" + CT.RESET);
		System.out.println(CT.F_GREEN + "member join			: 회원가입" + CT.RESET);

		System.out.println(CT.F_PURPLE + "\n=== 기타 명령어 ===" + CT.RESET);
		System.out.println(CT.F_RED + "exit				: 프로그램 종료" + CT.RESET);
	}
}
