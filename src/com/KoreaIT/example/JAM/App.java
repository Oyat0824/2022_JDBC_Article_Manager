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

		System.out.println(CT.FONT_GREEN);
		System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		System.out.println("█░░░░░░░░▀█▄▀▄▀██████░▀█▄▀▄▀██████░");
		System.out.println("░░░░░░░░░░░▀█▄█▄███▀░░░ ▀██▄█▄███▀░");
		System.out.println(">> JAM 시스템에 오신것을 환영합니다");
		System.out.println(CT.RESET);

		while (true) {
			System.out.print(CT.FONT_CYAN + "명령어 > " + CT.RESET);
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				System.out.println("[✔] 프로그램을 종료합니다.");
				break;
			}

			Connection conn = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("[✖] JDBC 드라이버 로딩 실패");
				break;
			}

			String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			String user = "root";
			String pw = "";

			try {
				conn = DriverManager.getConnection(url, user, pw);

				doAction(conn, sc, cmd);
			} catch (SQLException e) {
				System.out.println("[✖] DB 접속 에러 : " + e);
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
		// 게시글 작성
		if (cmd.equals("article write")) {
			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 작성 ==\n" + CT.RESET);
			System.out.print(CT.FONT_BLUE + "제목 : " + CT.RESET);
			String title = sc.nextLine();
			System.out.print(CT.FONT_BLUE + "내용 : " + CT.RESET);
			String body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);

			int id = DBUtil.insert(conn, sql);

			System.out.printf("[✔] %d번 게시글이 생성 되었습니다.\n", id);
		}
		// 게시글 열람
		else if (cmd.startsWith("article detail")) {
			int articleId = -1;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("[✖] 열람할 게시글 번호를 입력해주세요!");
				return;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", articleId);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.printf("[✖] 게시글이 존재하지 않습니다!\n");
				return;
			}
			
			Article article = new Article(articleMap);
			
			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 열람 ==\n" + CT.RESET);
			System.out.printf("번호 : %d\n", article.id);
			System.out.printf("제목 : %s\n", article.title);
			System.out.printf("작성일 : %s\n", article.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			System.out.printf("수정일 : %s\n", article.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			System.out.printf("--------------------------- \n");
			System.out.printf("%s\n", article.body);			
		}
		// 게시글 수정
		else if (cmd.startsWith("article modify")) {
			int articleId = -1;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("[✖] 검색할 게시글 번호를 입력해주세요!");
				return;
			}
			
			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", articleId);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.printf("[✖] %d번 게시글은 존재하지 않습니다!\n", articleId);
				return;
			}

			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 수정 ==\n" + CT.RESET);
			System.out.print(CT.FONT_BLUE + "수정할 제목 : " + CT.RESET);
			String title = sc.nextLine();
			System.out.print(CT.FONT_BLUE + "수정할 내용 : " + CT.RESET);
			String body = sc.nextLine();

			sql = new SecSql();

			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append("WHERE id = ?", articleId);

			DBUtil.update(conn, sql);

			System.out.printf("[✔] %d번 게시글이 수정 되었습니다.\n", articleId);
		}
		// 게시글 삭제
		else if (cmd.startsWith("article delete")) {
			int articleId = -1;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("[✖] 삭제할 게시글 번호를 입력해주세요!");
				return;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", articleId);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.printf("[✖] %d번 게시글은 존재하지 않습니다!\n", articleId);
				return;
			}

			sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", articleId);

			DBUtil.delete(conn, sql);

			System.out.printf(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== %d번 게시물 삭제 ==\n" + CT.RESET, articleId);
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
				System.out.println("[✖] 게시글이 존재하지 않습니다.");
				return;
			}
			
			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 목록 ==\n" + CT.RESET);
			System.out.println("번호	|	제목	|	작성일");
			for (Article article : articles) {
				System.out.printf("%d	|	%s\n", article.id, article.title);
			}
		}
	}
}
