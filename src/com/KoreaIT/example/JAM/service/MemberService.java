package com.KoreaIT.example.JAM.service;

import java.sql.Connection;

import com.KoreaIT.example.JAM.dao.MemberDao;

public class MemberService {
	private MemberDao memberDao;

	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
	}

	// 회원가입
	public int doJoin(String loginId, String loginPw, String name) {
		return memberDao.doJoin(loginId, loginPw, name);
	}

	// 로그인 아이디 중복 체크
	public boolean isLoginIdDupChk(String loginId) {
		return memberDao.isLoginIdDupChk(loginId);
	}

}
