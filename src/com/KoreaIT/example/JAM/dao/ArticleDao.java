package com.KoreaIT.example.JAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.Article;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleDao {
	public ArticleDao() {
		
	}

	// 작성
	public int doWrite(int memberId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", memberId = ?", memberId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		return DBUtil.insert(Container.conn, sql);
	}

	// 수정
	public int doModify(int articleId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", articleId);

		DBUtil.update(Container.conn, sql);
		
		return 0;
	}
	
	// 삭제
	public void doDelete(int articleId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", articleId);

		DBUtil.delete(Container.conn, sql);
	}
	
	// 게시글 존재 확인 (Boolean)
	public boolean isArticleExists(int articleId) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT COUNT(id) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", articleId);

		return DBUtil.selectRowBooleanValue(Container.conn, sql);
	}
	
	// 게시글 정보 가져오기
	public Article getArticle(int articleId) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT a.*, m.name AS writerName");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("WHERE a.id = ?", articleId);

		Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);
		
		if (articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}

	// 게시글들 정보 가져오기
	public List<Article> getArticles() {
		SecSql sql = new SecSql();

		sql.append("SELECT a.*, m.name AS writerName");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("ORDER BY a.id DESC");
		
		List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);

		List<Article> articles = new ArrayList<Article>();
		
		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		
		return articles;
	}
	
	// 게시글 조회수 증가
	public void increaseHit(int articleId) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", articleId);
		
		DBUtil.update(Container.conn, sql);
	}
}
