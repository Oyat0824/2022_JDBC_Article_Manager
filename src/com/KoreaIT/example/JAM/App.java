package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.example.JAM.mine.CT;

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
				
				if (cmd.equals("exit")) {
					System.out.println("[✔] 프로그램을 종료합니다.");
					break;
				}
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
		if (cmd.equals("article write")) {
			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 작성 ==\n" + CT.RESET);
			System.out.print(CT.FONT_BLUE + "제목 : " + CT.RESET);
			String title = sc.nextLine();
			System.out.print(CT.FONT_BLUE + "내용 : " + CT.RESET);
			String body = sc.nextLine();

			PreparedStatement pstmt = null;

			try {
				String sql = "INSERT INTO article";
				sql += " SET regDate = NOW()";
				sql += ", updateDate = NOW()";
				sql += ", title = '" + title + "'";
				sql += ", `body` = '" + body + "';";

				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("[✖] SQL 에러: " + e);
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			System.out.printf("[✔] 게시글이 생성 되었습니다.\n");
		} 
		else if (cmd.startsWith("article modify")) {
			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 수정 ==\n" + CT.RESET);
			int articleId = 0;

			try {
				articleId = Integer.parseInt(cmd.split(" ")[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("[✖] 검색할 게시글 번호를 입력해주세요!");
				return;
			}

			System.out.print(CT.FONT_BLUE + "수정할 제목 : " + CT.RESET);
			String title = sc.nextLine();
			System.out.print(CT.FONT_BLUE + "수정할 내용 : " + CT.RESET);
			String body = sc.nextLine();

			PreparedStatement pstmt = null;

			try {
				String sql = "UPDATE article";
				sql += " SET updateDate = NOW()";
				sql += ", title = '" + title + "'";
				sql += ", `body` = '" + body + "'";
				sql += " WHERE id = " + articleId;

				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				System.out.println("[✔] 수정 완료!");

			} catch (SQLException e) {
				System.out.println("[✖] SQL 에러 : " + e);
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else if (cmd.equals("article list")) {
			System.out.println(CT.BACKGROUND_BLACK + CT.FONT_WHITE + "== 게시물 목록 ==\n" + CT.RESET);

			PreparedStatement pstmt = null;
			ResultSet rs = null;

			List<Article> articles = new ArrayList<Article>();

			try {
				String sql = "SELECT *";
				sql += " FROM article";
				sql += " ORDER BY id DESC";

				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");

					Article article = new Article(id, regDate, updateDate, title, body);
					articles.add(article);
				}

			} catch (SQLException e) {
				System.out.println("[✖] SQL 에러 : " + e);
			} finally {
				try {
					if (rs != null && !rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (articles.isEmpty()) {
				System.out.println("[✖] 게시글이 존재하지 않습니다.");
				return;
			}

			System.out.println("번호	|	제목	");
			for (Article article : articles) {
				System.out.printf("%d	|	%s\n", article.id, article.title);
			}
		}
		
	}
}
