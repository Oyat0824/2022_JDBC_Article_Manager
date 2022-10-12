package com.KoreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
	
	// 열람*
	public Map<String, Object> showDetail(int articleId) {
		return articleDao.showDetail(articleId);
	}
	
	// 수정
	public int doModify(int articleId, String title, String body) {
		return articleDao.doModify(articleId, title, body);
	}
	
	// 삭제
	public void doDelete(int articleId) {
		articleDao.doDelete(articleId);
	}
	
	// 목록*
	public List<Map<String, Object>> showList() {
		return articleDao.showList();
	}

	// 게시글 존재 확인
	public boolean isArticleExists(int articleId) {
		return articleDao.isArticleExists(articleId);
	}
}
