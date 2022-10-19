package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.controller.ArticleController;
import com.KoreaIT.example.JAM.controller.MemberController;
import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.mine.Help;

public class App {
	public void run() {
		Container.sc = new Scanner(System.in);
		
		Container.init();
		
		System.out.println(CT.F_GREEN);
		System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		System.out.println("█░░░░░░░░▀█▄▀▄▀██████░▀█▄▀▄▀██████░");
		System.out.println("░░░░░░░░░░░▀█▄█▄███▀░░░ ▀██▄█▄███▀░");
		System.out.println(">> JAM 시스템에 오신것을 환영합니다");
		System.out.print(CT.RESET);

		while (true) {
			CT.CyanT("\n명령어 > ");
			String cmd = Container.sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				CT.CrossMark("JDBC 드라이버 로딩 실패");
				break;
			}

			String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			String user = "root";
			String pw = "";

			try {
				conn = DriverManager.getConnection(url, user, pw);
				Container.conn = conn;
				
				// 프로그램 종료
				if (cmd.equals("exit")) {
					CT.CheckMark("프로그램을 종료합니다.");
					break;
				}
				
				// 명령어 실행
				doAction(cmd);
				
			} catch (SQLException e) {
				CT.CrossMark("DB 접속 에러 : " + e);
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

		Container.sc.close();
	}

	private void doAction(String cmd) {
		MemberController memberController = Container.memberController;
		ArticleController articleController = Container.articleController;
		
		// 회원가입 기능
		if (cmd.equals("member join")) {
			memberController.doJoin(cmd);
		}
		// 로그인 기능
		else if (cmd.equals("member login")) {
			memberController.doLogin(cmd);
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
		// 도움말
		else if (cmd.startsWith("help")) {
			Help.showHelp(cmd);
		}
		// 명령어가 없는 경우
		else {
			CT.CrossMark("존재하지 않는 명령어입니다!");
			return;
		}
	}
}
