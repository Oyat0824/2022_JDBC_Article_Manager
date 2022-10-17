package com.KoreaIT.example.JAM.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.service.ArticleService;

public class ArticleController extends Controller {
	private ArticleService articleService;
	
	public ArticleController() {
		articleService = Container.articleService;
	}
	
	// 게시글 작성
	public void doWrite(String cmd) {
		CT.PurpleTL("=== 게시글 작성 ===");
		String title = null;
		String body = null;
		
		while(true) {
			CT.BlueT("제목 : ");
			title = sc.nextLine();
			
			if(title.isEmpty()) {
				CT.CrossMark("게시글 제목을 입력해주세요!");
				continue;
			}
			
			break;
		}
		
		while(true) {
			CT.BlueT("내용 : ");
			body = sc.nextLine();
			
			if(body.isEmpty()) {
				CT.CrossMark("게시글 내용을 입력해주세요!");
				continue;
			}
			
			break;
		}

		int id = articleService.doWrite(title, body);
		
		CT.CheckMark("%d번 게시글이 생성 되었습니다.", id);
	}
	
	// 게시글 열람
	public void showDetail(String cmd) {
		int articleId = -1;

		try {
			articleId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			CT.CrossMark("열람할 게시글 번호를 입력해주세요!");
			return;
		}
		
		Article article = articleService.getArticle(articleId);
		
		if(article == null) {
			CT.CrossMark("%d번 게시글은 존재하지 않습니다!", articleId);
			return;
		}
		
		CT.PurpleTL("=== 게시글 열람 ===");
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
			CT.CrossMark("검색할 게시글 번호를 입력해주세요!");
			return;
		}

		boolean isArticleExists = articleService.isArticleExists(articleId);

		if (isArticleExists == false) {
			CT.CrossMark("%d번 게시글은 존재하지 않습니다!", articleId);
			return;
		}

		CT.PurpleTL("=== 게시글 수정 ===");
		String title = null;
		String body = null;
		
		while(true) {
			CT.BlueT("수정할 제목 : ");
			title = sc.nextLine();
			
			if(title.isEmpty()) {
				CT.CrossMark("수정할 게시글 제목을 입력해주세요!");
				continue;
			}
			
			break;
		}
		
		while(true) {
			CT.BlueT("수정할 내용 : ");
			body = sc.nextLine();
			
			if(body.isEmpty()) {
				CT.CrossMark("수정할 게시글 내용을 입력해주세요!");
				continue;
			}
			
			break;
		}
		
		articleService.doModify(articleId, title, body);

		CT.CheckMark("%d번 게시글이 수정 되었습니다.", articleId);
	}

	// 게시글 삭제
	public void doDelete(String cmd) {
		int articleId = -1;

		try {
			articleId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			CT.CrossMark("삭제할 게시글 번호를 입력해주세요!");
			return;
		}

		boolean isArticleExists = articleService.isArticleExists(articleId);

		if (isArticleExists == false) {
			CT.CrossMark("%d번 게시글은 존재하지 않습니다!", articleId);
			return;
		}

		articleService.doDelete(articleId);
		
		CT.CheckMark("%d번 게시물 삭제", articleId);
	}

	// 게시글 목록
	public void showList(String cmd) {
		List<Article> articles = articleService.getArticles();

		if (articles.isEmpty()) {
			CT.CrossMark("게시글이 존재하지 않습니다!");
			return;
		}

		System.out.println(CT.F_PURPLE + "=== 게시물 목록 ===" + CT.RESET);
		System.out.println(CT.F_BLUE + "번호	|	제목" + CT.RESET);
		for (Article article : articles) {
			System.out.printf("%d	|	%s\n", article.id, article.title);
		}
	}
}
