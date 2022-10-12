package com.KoreaIT.example.JAM.dao;

import java.sql.Connection;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberDao {
	private Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}
	
	// 회원가입
	public int doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);

		return DBUtil.insert(conn, sql);
	}
	
	// 로그인 아이디 중복 체크
	public boolean isLoginIdDupChk(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(loginId) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowBooleanValue(conn, sql);
	}
}
