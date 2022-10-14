package com.KoreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.List;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.dao.ArticleDao;

public class ArticleService {
	private ArticleDao articleDao;
	
	public ArticleService(Connection conn) {
		articleDao = new ArticleDao(conn);
	}
	
	// 작성
	public int doWrite(String title, String body) {
		return articleDao.doWrite(title, body);
	}
	
	// 수정
	public int doModify(int articleId, String title, String body) {
		return articleDao.doModify(articleId, title, body);
	}
	
	// 삭제
	public void doDelete(int articleId) {
		articleDao.doDelete(articleId);
	}

	// 게시글 존재 확인 (Boolean)
	public boolean isArticleExists(int articleId) {
		return articleDao.isArticleExists(articleId);
	}
	
	// 게시글 존재 확인
	public Article getArticle(int articleId) {
		return articleDao.getArticle(articleId);
	}

	// 게시글들 존재 확인
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}
}
