package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.service.ArticleService;

public class ArticleController extends Controller {
	private ArticleService articleService;
	
	public ArticleController(Connection conn, Scanner sc) {
		super(sc);
		this.articleService = new ArticleService(conn);
	}
	
	// 게시글 작성
	public void doWrite(String cmd) {
		System.out.println(CT.F_PURPLE + "=== 게시글 작성 ===" + CT.RESET);
		String title = null;
		String body = null;
		
		while(true) {
			System.out.print(CT.F_BLUE + "제목 : " + CT.RESET);
			title = sc.nextLine();
			
			if(title.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "게시글 제목을 입력해주세요!");
				continue;
			}
			
			break;
		}
		
		while(true) {
			System.out.print(CT.F_BLUE + "내용 : " + CT.RESET);
			body = sc.nextLine();
			
			if(body.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "게시글 내용을 입력해주세요!");
				continue;
			}
			
			break;
		}

		int id = articleService.doWrite(title, body);

		System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%d번 게시글이 생성 되었습니다.\n", id);
	}
	
	// 게시글 열람
	public void showDetail(String cmd) {
		int articleId = -1;

		try {
			articleId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(CT.F_RED + "[✖] " + CT.RESET + "열람할 게시글 번호를 입력해주세요!");
			return;
		}
		
		Article article = articleService.getArticle(articleId);
		
		if(article == null) {
			System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "%d번 게시글은 존재하지 않습니다!\n", articleId);
			return;
		}

		System.out.println(CT.F_PURPLE + "=== 게시글 열람 ===" + CT.RESET);
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("작성일 : %s\n", article.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.printf("수정일 : %s\n", article.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.printf("--------------------------- \n");
		System.out.printf("%s\n", article.body);
	}

	// 게시글 수정
	public void doModify(String cmd) {
		int articleId = -1;

		try {
			articleId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(CT.F_RED + "[✖] " + CT.RESET + "검색할 게시글 번호를 입력해주세요!");
			return;
		}

		boolean isArticleExists = articleService.isArticleExists(articleId);

		if (isArticleExists == false) {
			System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "%d번 게시글은 존재하지 않습니다!\n", articleId);
			return;
		}

		System.out.println(CT.F_PURPLE + "=== 게시글 수정 ===" + CT.RESET);
		String title = null;
		String body = null;
		
		while(true) {
			System.out.print(CT.F_BLUE + "수정할 제목 : " + CT.RESET);
			title = sc.nextLine();
			
			if(title.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "수정할 게시글 제목을 입력해주세요!");
				continue;
			}
			
			break;
		}
		
		while(true) {
			System.out.print(CT.F_BLUE + "수정할 내용 : " + CT.RESET);
			body = sc.nextLine();
			
			if(body.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "수정할 게시글 내용을 입력해주세요!");
				continue;
			}
			
			break;
		}
		
		articleService.doModify(articleId, title, body);

		System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%d번 게시글이 수정 되었습니다.\n", articleId);
	}

	// 게시글 삭제
	public void doDelete(String cmd) {
		int articleId = -1;

		try {
			articleId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(CT.F_RED + "[✖] " + CT.RESET + "삭제할 게시글 번호를 입력해주세요!");
			return;
		}

		boolean isArticleExists = articleService.isArticleExists(articleId);

		if (isArticleExists == false) {
			System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "%d번 게시글은 존재하지 않습니다!\n", articleId);
			return;
		}

		articleService.doDelete(articleId);
		
		System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "%d번 게시물 삭제\n" + CT.RESET, articleId);
	}

	// 게시글 목록
	public void showList(String cmd) {
		List<Article> articles = articleService.getArticles();

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
}
