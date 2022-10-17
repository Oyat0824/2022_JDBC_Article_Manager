package com.KoreaIT.example.JAM.mine;

public class CT {
	public static final String RESET = "\u001B[0m";
	public static final String F_BLACK = "\u001B[30m";
	public static final String F_RED = "\u001B[31m";
	public static final String F_GREEN = "\u001B[32m";
	public static final String F_YELLOW = "\u001B[33m";
	public static final String F_BLUE = "\u001B[34m";
	public static final String F_PURPLE = "\u001B[35m";
	public static final String F_CYAN = "\u001B[36m";
	public static final String F_WHITE = "\u001B[37m";
	public static final String BG_BLACK = "\u001B[40m";
	public static final String BG_RED = "\u001B[41m";
	public static final String BG_GREEN = "\u001B[42m";
	public static final String BG_YELLOW = "\u001B[43m";
	public static final String BG_BLUE = "\u001B[44m";
	public static final String BG_PURPLE = "\u001B[45m";
	public static final String BG_CYAN = "\u001B[46m";
	public static final String BG_WHITE = "\u001B[47m";
	
	// 한줄 텍스트
	public static void BlueT(String text) {
		System.out.printf(CT.F_CYAN + text + CT.RESET);
	}
	
	public static void CyanT(String text) {
		System.out.printf(CT.F_CYAN + text + CT.RESET);
	}
	
	// 텍스트 라인(끝에 엔터 추가)
	public static void BlueTL(String textLine) {
		System.out.println(CT.F_BLUE + textLine + CT.RESET);
	}
	
	public static void CyanTL(String textLine) {
		System.out.println(CT.F_CYAN + textLine + CT.RESET);
	}
	
	public static void GreenTL(String textLine) {
		System.out.println(CT.F_GREEN + textLine + CT.RESET);
	}
	
	public static void PurpleTL(String textLine) {
		System.out.println(CT.F_PURPLE + textLine + CT.RESET);
	}
	
	public static void RedTL(String textLine) {
		System.out.println(CT.F_RED + textLine + CT.RESET);
	}
	
	public static void YellowTL(String textLine) {
		System.out.println(CT.F_YELLOW + textLine + CT.RESET);
	}
	
	// 체크 마크
	public static void CheckMark(String textLine) {
		System.out.println(CT.F_GREEN + "[✔] " + CT.RESET + textLine);
	}
	
	public static void CheckMark(String textLine, int args) {
		System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + textLine + "\n", args);
	}
	
	public static void CheckMark(String textLine, String args) {
		System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + textLine + "\n", args);
	}

	// 크로스 마크
	public static void CrossMark(String textLine) {
		System.out.println(CT.F_RED + "[✖] " + CT.RESET + textLine);
	}
	
	public static void CrossMark(String textLine, int args) {
		System.out.printf(CT.F_RED + "[✖] " + CT.RESET + textLine + "\n", args);
	}
	
	public static void CrossMark(String textLine, String args) {
		System.out.printf(CT.F_RED + "[✖] " + CT.RESET + textLine + "\n", args);
	}

	
}
