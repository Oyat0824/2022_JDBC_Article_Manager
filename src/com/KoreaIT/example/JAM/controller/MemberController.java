package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.Member;
import com.KoreaIT.example.JAM.mine.CT;
import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController(Connection conn, Scanner sc) {
		super(sc);
		this.memberService = new MemberService(conn);
	}

	// 회원 가입 기능
	public void doJoin(String cmd) {
		String loginId = null;
		String loginPw = null;
		String loginPwChk = null;
		String name = null;

		System.out.println(CT.F_PURPLE + "=== 회원 가입 ===" + CT.RESET);

		// 아이디 입력 부분
		while (true) {
			System.out.print(CT.F_CYAN + "가입 ID : " + CT.RESET);
			loginId = sc.nextLine().trim();

			if (loginId.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "아이디를 입력해주세요!");
				continue;
			}

			boolean isLoginIdDupChk = memberService.isLoginIdDup(loginId);

			if (isLoginIdDupChk) {
				System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "< %s > 아이디는 이미 존재하는 아이디입니다!\n", loginId);
				continue;
			}

			break;
		}

		// 비밀번호 입력 부분
		while (true) {
			System.out.print(CT.F_CYAN + "비밀번호 : " + CT.RESET);
			loginPw = sc.nextLine().trim();

			if (loginPw.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호를 입력해주세요!");
				continue;
			}

			boolean isLoginPwChk = true;

			// 비밀번호 확인 입력 부분
			while (true) {
				System.out.print(CT.F_CYAN + "비밀번호 확인 : " + CT.RESET);
				loginPwChk = sc.nextLine().trim();

				if (loginPwChk.isEmpty()) {
					System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호 확인을 입력해주세요!");
					continue;
				}

				if (loginPw.equals(loginPwChk) == false) {
					System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호가 일치하지 않습니다!");
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
			System.out.print(CT.F_CYAN + "이름 : " + CT.RESET);
			name = sc.nextLine().trim();

			if (name.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "이름을 입력해주세요!");
				continue;
			}

			break;
		}

		memberService.doJoin(loginId, loginPw, name);

		System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "< %s >님, 회원가입이 완료되었습니다.\n", name);
	}

	public void doLogin(String cmd) {
		String loginId = null;
		String loginPw = null;
		
		System.out.println(CT.F_PURPLE + "=== 로그인 ===" + CT.RESET);

		// 아이디 입력 부분
		while (true) {
			System.out.print(CT.F_CYAN + "로그인 ID : " + CT.RESET);
			loginId = sc.nextLine().trim();

			if (loginId.isEmpty()) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "아이디를 입력해주세요!");
				continue;
			}

			boolean isLoginIdDupChk = memberService.isLoginIdDup(loginId);

			if (isLoginIdDupChk == false) {
				System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "< %s > 해당 아이디는 존재하지 않는 아이디입니다!\n", loginId);
				continue;
			}

			break;
		}
		
		Member member = memberService.getMember(loginId);
		int tryCount = 0;
		int tryMaxCount = 3;

		// 비밀번호 입력 부분
		while (true) {
			if(tryCount >= tryMaxCount) {
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호를 확인하고 다시 시도해주세요!");
				break;
			}
			
			System.out.print(CT.F_CYAN + "로그인 PW : " + CT.RESET);
			loginPw = sc.nextLine().trim();
			
			if (loginId.isEmpty()) {
				tryCount++;
				System.out.println(CT.F_RED + "[✖] " + CT.RESET + "비밀번호를 입력해주세요!");
				continue;
			}

			if (loginPw.equals(member.loginPw) == false) {
				tryCount++;
				System.out.printf(CT.F_RED + "[✖] " + CT.RESET + "비밀번호가 일치하지 않습니다.\n");
				continue;
			}

			System.out.printf(CT.F_GREEN + "[✔] " + CT.RESET + "< %s >님, 환영합니다.\n", member.name);
		}
		
	}
}
