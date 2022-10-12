package com.KoreaIT.example.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class ArticleDao {
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	// 작성
	public int doWrite(String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		return DBUtil.insert(conn, sql);
	}

	// 열람*
	public Map<String, Object> showDetail(int articleId) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", articleId);
		
		return DBUtil.selectRow(conn, sql);
	}

	// 수정
	public int doModify(int articleId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", articleId);

		DBUtil.update(conn, sql);
		
		return 0;
	}
	
	// 삭제
	public void doDelete(int articleId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", articleId);

		DBUtil.delete(conn, sql);
	}

	// 목록*
	public List<Map<String, Object>> showList() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		return DBUtil.selectRows(conn, sql);
	}
	
	// 게시글 존재 확인
	public boolean isArticleExists(int articleId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", articleId);

		return DBUtil.selectRowBooleanValue(conn, sql);
	}
}
