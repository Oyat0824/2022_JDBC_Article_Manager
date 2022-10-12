package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.example.JAM.controller.ArticleController;
import com.KoreaIT.example.JAM.controller.MemberController;
import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.mine.Help;

public class App {
	public void run() {
		Scanner sc = new Scanner(System.in);

		System.out.println(CT.F_GREEN);
		System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		System.out.println("█░░░░░░░░▀█▄▀▄▀██████░▀█▄▀▄▀██████░");
		System.out.println("░░░░░░░░░░░▀█▄█▄███▀░░░ ▀██▄█▄███▀░");
		System.out.println(">> JAM 시스템에 오신것을 환영합니다");
		System.out.print(CT.RESET);

		while (true) {
			System.out.printf(CT.F_CYAN + "\n명령어 > " + CT.RESET);
			String cmd = sc.nextLine().trim();

			// 도움말
			if (cmd.startsWith("help")) {
				Help.showHelp(cmd);
				continue;
			}
			
			if (cmd.equals("exit")) {
				System.out.println(CT.F_GREEN + "[✔] " + CT.RESET + "프로그램을 종료합니다.");
				break;
			}

			Connection conn = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "JDBC 드라이버 로딩 실패");
				break;
			}

			String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			String user = "root";
			String pw = "";

			try {
				conn = DriverManager.getConnection(url, user, pw);

				doAction(conn, sc, cmd);
			} catch (SQLException e) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "DB 접속 에러 : " + e);
				break;
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		sc.close();
	}

	private void doAction(Connection conn, Scanner sc, String cmd) {
		MemberController memberController = new MemberController(conn, sc);
		ArticleController articleController = new ArticleController(conn, sc);
		
		// 회원가입 기능
		if (cmd.equals("member join")) {
			memberController.doJoin(cmd);
		}
		// 게시글 작성
		else if (cmd.equals("article write")) {
			articleController.doWrite(cmd);
		}
		// 게시글 열람
		else if (cmd.startsWith("article detail")) {
			articleController.showDetail(cmd);
		}
		// 게시글 수정
		else if (cmd.startsWith("article modify")) {
			articleController.doModify(cmd);
		}
		// 게시글 삭제
		else if (cmd.startsWith("article delete")) {
			articleController.doDelete(cmd);
		}
		// 게시글 목록
		else if (cmd.equals("article list")) {
			articleController.showList(cmd);
		}
		// 명령어가 없는 경우
		else {
			System.out.println(CT.F_RED + "[✖] " + CT.RESET + "존재하지 않는 명령어입니다!");
			return;
		}
	}
}
