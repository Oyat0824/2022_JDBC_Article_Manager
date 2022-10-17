package com.KoreaIT.example.JAM.mine;

public class Help {
	public static void showHelp(String cmd) {
		int num = -1;

		try {
			num = Integer.parseInt(cmd.split(" ")[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			CT.PurpleTL("보고싶은 명령어 목록을 골라주세요.");
			CT.YellowTL("명령어 > help [번호]\n");
			CT.BlueTL("1. 게시글 전용 명령어");
			CT.GreenTL("2. 회원 전용 명령어");
			CT.CyanTL("3. 기타 명령어");
			
			return;
		}
		
		if(num == 1) {
			CT.PurpleTL("=== 게시글 전용 명령어 ===");
			CT.GreenTL( "article write			: 게시글 작성");
			CT.YellowTL("article detail [번호]		: 게시글 열람");
			CT.GreenTL( "article modify [번호]		: 게시글 수정");
			CT.YellowTL("article delete [번호]		: 게시글 삭제");
			CT.GreenTL( "article list			: 게시글 목록");
			
			return;
		}
		else if(num == 2) {
			CT.PurpleTL("\n=== 회원 전용 명령어 ===");
			CT.GreenTL( "member join			: 회원가입");
			CT.YellowTL("member login			: 로그인");
			
			return;
		}
		else if(num == 3) {
			CT.PurpleTL("\n=== 기타 명령어 ===");
			CT.RedTL("help [번호]			: 도움말 확인");
			CT.RedTL("exit				: 프로그램 종료");

			return;
		}
		else {
			CT.CrossMark("해당하는 번호의 명령어 목록이 존재하지 않습니다!");
			
			return;
		}
	}
}
