package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Article> articles = new ArrayList<Article>();
		int lastArticleId = 0;

		System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
		System.out.println("█░░░░░░░░▀█▄▀▄▀██████░▀█▄▀▄▀██████░");
		System.out.println("░░░░░░░░░░░▀█▄█▄███▀░░░ ▀██▄█▄███▀░");
		System.out.println(">> JAM 시스템에 오신것을 환영합니다\n");

		while (true) {
			System.out.print("명령어 > ");

			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			if (cmd.equals("article write")) {
				System.out.println("== 게시물 작성 ==\n");
				int id = lastArticleId + 1;

				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, title, body);
				articles.add(article);

				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");

					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW()";
					sql += ", updateDate = NOW()";
					sql += ", title = '"+ title +"'";
					sql += ", `body` = '" + body + "';";
					
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					pstmt.executeUpdate();

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} finally {
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				lastArticleId++;

				System.out.printf("[✔] %d번 글이 생성 되었습니다.\n", id);
			} else if (cmd.equals("article list")) {
				System.out.println("== 게시물 리스트 ==\n");
				if (articles.isEmpty()) {
					System.out.println("[✖] 게시글이 존재하지 않습니다.");
					continue;
				}

				System.out.println("번호	|	제목	");
				for (Article article : articles) {
					System.out.printf("%d	|	%s\n", article.id, article.title);
				}
			}
		}

		sc.close();
	}
}