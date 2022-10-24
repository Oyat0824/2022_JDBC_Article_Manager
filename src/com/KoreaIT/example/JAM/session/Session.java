package com.KoreaIT.example.JAM.session;

import com.KoreaIT.example.JAM.Member;

public class Session {
	public int loginedMemberId;
	public Member loginedMember;
	
	public Session() {
		loginedMemberId = -1;
	}

	public void login(Member member) {
		loginedMemberId = member.id;
		loginedMember = member;
	}

	public void logout() {
		loginedMemberId = -1;
		loginedMember = null;
	}

	public boolean isLogined() {
		return loginedMemberId != -1;
	}
}
