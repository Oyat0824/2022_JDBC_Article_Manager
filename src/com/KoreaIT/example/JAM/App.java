package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

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
		// 도움말
		if (cmd.equals("help")) {
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

		// 게시글 작성
		if (cmd.equals("article write")) {
			System.out.println(CT.F_PURPLE + "=== 게시글 작성 ===" + CT.RESET);
			System.out.print(CT.F_BLUE + "제목 : " + CT.RESET);
			String title = sc.nextLine();
			System.out.print(CT.F_BLUE + "내용 : " + CT.RESET);
			String body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);

			int id = DBUtil.insert(conn, sql);

			System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%d번 게시글이 생성 되었습니다.\n", id);
		}
		// 게시글 열람
		else if (cmd.startsWith("article detail")) {
			int articleId = -1;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "열람할 게시글 번호를 입력해주세요!");
				return;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", articleId);

			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "게시글이 존재하지 않습니다!\n");
				return;
			}

			Article article = new Article(articleMap);

			System.out.println(CT.F_PURPLE + "=== 게시글 열람 ===" + CT.RESET);
			System.out.printf("번호 : %d\n", article.id);
			System.out.printf("제목 : %s\n", article.title);
			System.out.printf("작성일 : %s\n", article.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			System.out.printf("수정일 : %s\n",
					article.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			System.out.printf("--------------------------- \n");
			System.out.printf("%s\n", article.body);
		}
		// 게시글 수정
		else if (cmd.startsWith("article modify")) {
			int articleId = -1;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "검색할 게시글 번호를 입력해주세요!");
				return;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", articleId);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "%d번 게시글은 존재하지 않습니다!\n", articleId);
				return;
			}

			System.out.println(CT.F_PURPLE + "=== 게시글 수정 ===" + CT.RESET);
			System.out.print(CT.F_BLUE + "수정할 제목 : " + CT.RESET);
			String title = sc.nextLine();
			System.out.print(CT.F_BLUE + "수정할 내용 : " + CT.RESET);
			String body = sc.nextLine();

			sql = new SecSql();

			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append("WHERE id = ?", articleId);

			DBUtil.update(conn, sql);

			System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%d번 게시글이 수정 되었습니다.\n", articleId);
		}
		// 게시글 삭제
		else if (cmd.startsWith("article delete")) {
			int articleId = -1;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "삭제할 게시글 번호를 입력해주세요!");
				return;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", articleId);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "%d번 게시글은 존재하지 않습니다!\n", articleId);
				return;
			}

			sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", articleId);

			DBUtil.delete(conn, sql);
			
			System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%d번 게시물 삭제\n" + CT.RESET, articleId);
		}
		// 게시글 목록
		else if (cmd.equals("article list")) {
			List<Article> articles = new ArrayList<Article>();

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC");

			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> articleMap : articleListMap) {
				articles.add(new Article(articleMap));
			}

			if (articles.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "게시글이 존재하지 않습니다!");
				return;
			}

			System.out.println(CT.F_PURPLE + "=== 게시물 목록 ===" + CT.RESET);
			System.out.println(CT.F_BLUE + "번호	|	제목" + CT.RESET);
			for (Article article : articles) {
				System.out.printf("%d	|	%s\n", article.id, article.title);
			}
		}
		// 회원가입 기능
		else if (cmd.equals("member join")) {
			String loginId = null;
			String loginPw = null;
			String loginPwChk = null;
			String name = null;
			
			System.out.println(CT.F_PURPLE + "=== 회원 가입 ===" + CT.RESET);
			while(true) {
			// 아이디 입력 확인 부분
				System.out.print(CT.F_CYAN + "가입 ID : " + CT.RESET);
				loginId = sc.nextLine().trim();
				
				if(loginId.isEmpty()) {
					System.out.println(CT.F_RED + "[✖] " + CT.RESET + "아이디를 입력해주세요!");
					continue;
				}
				
				SecSql sql = new SecSql();

				sql.append("SELECT COUNT(loginId) > 0");
				sql.append("FROM `member`");
				sql.append("WHERE loginId = ?", loginId);
				
				boolean isLoginIdDupChk = DBUtil.selectRowBooleanValue(conn, sql);
				
				if(isLoginIdDupChk) {
					System.out.println(CT.F_RED + "[✖] " + CT.RESET + "이미 존재하는 아이디입니다!");
					continue;
				}
				
				break;
			}
			
			while(true) {
			// 비밀번호 입력 확인 부분
				System.out.print(CT.F_CYAN + "비밀번호 : " + CT.RESET);
				loginPw = sc.nextLine().trim();
				
				if(loginPw.isEmpty()) {
					System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호를 입력해주세요!");
					continue;
				}
				
				boolean isLoginPwChk = true;
				
				while(true) {
					System.out.print(CT.F_CYAN + "비밀번호 확인 : " + CT.RESET);
					loginPwChk = sc.nextLine().trim();
					
					if(loginPwChk.isEmpty()) {
						System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호 확인을 입력해주세요!");
						continue;
					}
					
					if(loginPw.equals(loginPwChk) == false) {
						System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호가 일치하지 않습니다!");
						isLoginPwChk = false;
					}
					
					break;
				}
				
				if(isLoginPwChk) {
					break;
				}
			}
			
			while(true) {
			// 이름 입력 확인 부분
				System.out.print(CT.F_CYAN + "이름 : " + CT.RESET);
				name = sc.nextLine().trim();
				
				if(name.isEmpty()) {
					System.out.println(CT.F_RED + "[✖] " + CT.RESET + "이름을 입력해주세요!");
					continue;
				}
				
				break;
			}
			
			SecSql sql = new SecSql();

			sql.append("INSERT INTO `member`");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", loginId = ?", loginId);
			sql.append(", loginPw = ?", loginPw);
			sql.append(", `name` = ?", name);
			
			DBUtil.insert(conn, sql);
			
			System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%s 님, 회원가입이 완료되었습니다.\n", name);
			
		}
	}
}
