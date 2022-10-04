package com.KoreaIT.example.JAM;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("JAM 시스템에 오신것을 환영합니다, 명령어를 입력해주세요.\n");
		
		while(true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();
			
			if(cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			
		}
		
		sc.close();
	}
}