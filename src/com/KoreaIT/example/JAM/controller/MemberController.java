package com.KoreaIT.example.JAM.controller;

import com.KoreaIT.example.JAM.Member;
import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	// 회원 가입 기능
	public void doJoin(String cmd) {
		String loginId = null;
		String loginPw = null;
		String loginPwChk = null;
		String name = null;

		CT.PurpleTL("=== 회원 가입 ===");

		// 아이디 입력 부분
		while (true) {
			CT.CyanT("가입 ID : ");
			loginId = sc.nextLine().trim();

			if (loginId.isEmpty()) {
				CT.CrossMark("아이디를 입력해주세요!");
				continue;
			}

			boolean isLoginIdDupChk = memberService.isLoginIdDup(loginId);

			if (isLoginIdDupChk) {
				CT.CrossMark("< %s > 아이디는 이미 존재하는 아이디입니다!", loginId);
				continue;
			}

			break;
		}

		// 비밀번호 입력 부분
		while (true) {
			CT.CyanT("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.isEmpty()) {
				CT.CrossMark("비밀번호를 입력해주세요!");
				continue;
			}

			boolean isLoginPwChk = true;

			// 비밀번호 확인 입력 부분
			while (true) {
				CT.CyanT("비밀번호 확인 : ");
				loginPwChk = sc.nextLine().trim();

				if (loginPwChk.isEmpty()) {
					CT.CrossMark("비밀번호 확인을 입력해주세요!");
					continue;
				}

				if (loginPw.equals(loginPwChk) == false) {
					CT.CrossMark("비밀번호가 일치하지 않습니다!");
					isLoginPwChk = false;
				}

				break;
			}

			if (isLoginPwChk) {
				break;
			}
		}

		// 이름 입력 부분
		while (true) {
			CT.CyanT("이름 : ");
			name = sc.nextLine().trim();

			if (name.isEmpty()) {
				CT.CrossMark("이름을 입력해주세요!");
				continue;
			}

			break;
		}

		memberService.doJoin(loginId, loginPw, name);

		CT.CheckMark("< %s >님, 회원가입이 완료되었습니다.\n", name);
	}

	public void doLogin(String cmd) {
		String loginId = null;
		String loginPw = null;
		
		CT.PurpleTL("=== 로그인 ===");

		// 아이디 입력 부분
		while (true) {
			CT.CyanT("로그인 ID : ");
			loginId = sc.nextLine().trim();

			if (loginId.isEmpty()) {
				CT.CrossMark("아이디를 입력해주세요!");
				continue;
			}

			boolean isLoginIdDupChk = memberService.isLoginIdDup(loginId);

			if (isLoginIdDupChk == false) {
				CT.CrossMark("< %s > 해당 아이디는 존재하지 않는 아이디입니다!\n", loginId);
				continue;
			}

			break;
		}
		
		Member member = memberService.getMember(loginId);
		int tryCount = 0;		// 로그인 시도 횟수
		int tryMaxCount = 3;	// 최대 로그인 시도 횟수

		// 비밀번호 입력 부분
		while (true) {
			if(tryCount >= tryMaxCount) {
				CT.CrossMark("비밀번호를 확인하고 다시 시도해주세요!");
				break;
			}
			
			CT.CyanT("로그인 PW : ");
			loginPw = sc.nextLine().trim();
			
			if (loginId.isEmpty()) {
				tryCount++;
				CT.CrossMark("비밀번호를 입력해주세요!");
				continue;
			}

			if (loginPw.equals(member.loginPw) == false) {
				tryCount++;
				CT.CrossMark("비밀번호가 일치하지 않습니다.");
				continue;
			}

			CT.CheckMark("< %s >님, 환영합니다.\n", member.name);
		}
		
	}
}
