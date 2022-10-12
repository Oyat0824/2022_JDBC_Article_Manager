package com.KoreaIT.example.JAM.mine;

public class Help {
	public static void showHelp(String cmd) {
		int num = -1;

		try {
			num = Integer.parseInt(cmd.split(" ")[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(CT.F_PURPLE + "보고싶은 명령어 목록을 골라주세요." + CT.RESET);
			System.out.println(CT.F_YELLOW + "명령어 > help [번호]\n" + CT.RESET);
			System.out.println(CT.F_BLUE + "1. 게시글 전용 명령어" + CT.RESET);
			System.out.println(CT.F_GREEN + "2. 회원 전용 명령어" + CT.RESET);
			System.out.println(CT.F_CYAN + "3. 기타 명령어" + CT.RESET);
			return;
		}
		
		if(num == 1) {
			System.out.println(CT.F_PURPLE + "=== 게시글 전용 명령어 ===" + CT.RESET);
			System.out.println(CT.F_GREEN + "article write			: 게시글 작성" + CT.RESET);
			System.out.println(CT.F_YELLOW + "article detail [번호]		: 게시글 열람" + CT.RESET);
			System.out.println(CT.F_GREEN + "article modify [번호]		: 게시글 수정" + CT.RESET);
			System.out.println(CT.F_YELLOW + "article delete [번호]		: 게시글 삭제" + CT.RESET);
			System.out.println(CT.F_GREEN + "article list			: 게시글 목록" + CT.RESET);	
			return;
		}
		else if(num == 2) {
			System.out.println(CT.F_PURPLE + "\n=== 회원 전용 명령어 ===" + CT.RESET);
			System.out.println(CT.F_GREEN + "member join			: 회원가입" + CT.RESET);
			return;
		}
		else if(num == 3) {
			System.out.println(CT.F_PURPLE + "\n=== 기타 명령어 ===" + CT.RESET);
			System.out.println(CT.F_RED + "help [번호]			: 도움말 확인" + CT.RESET);
			System.out.println(CT.F_RED + "exit				: 프로그램 종료" + CT.RESET);
			return;
		}
		else {
			System.out.println(CT.F_RED + "[✖] " + CT.RESET + "해당하는 번호의 명령어 목록이 존재하지 않습니다!");
			return;
		}
	}
}
