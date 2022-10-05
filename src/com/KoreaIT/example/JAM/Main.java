package com.KoreaIT.example.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Article> articles = new ArrayList<Article>();
		int lastArticleId = 0;
		
		System.out.println("JAM 시스템에 오신것을 환영합니다, 명령어를 입력해주세요.\n");
		
		while(true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();
			
			if(cmd.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			if(cmd.equals("article write")) {
				System.out.println("== 게시물 작성 ==\n");
				int id = lastArticleId + 1;
				
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body);
				articles.add(article);
				
				lastArticleId++;

				System.out.printf("[✔] %d번 글이 생성 되었습니다.\n", id);
			}
			else if(cmd.equals("article list")) {
				System.out.println("== 게시물 리스트 ==\n");
				if(articles.isEmpty()) {
					System.out.println("[✖] 게시글이 존재하지 않습니다.");
					continue;
				}
				
				System.out.println("번호	|	제목	");
				for(Article article : articles) {
					System.out.printf("%d	|	%s\n", article.id, article.title);
				}
			}
		}
		
		sc.close();
	}
}