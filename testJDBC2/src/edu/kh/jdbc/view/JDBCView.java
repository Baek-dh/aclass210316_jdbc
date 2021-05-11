package edu.kh.jdbc.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.model.vo.Member;

/**
 * JDBC 실습용 View 클래스
 * @author 백동현 (beakdh@iei.or.kr)
 */
public class JDBCView {
	
	// 로그인한 회원의 정보를 저장할 변수
	public static Member loginMember = null;
	// --> 이 변수를 사용하는 방법
	// JDBCView.loginMember  (static 변수는 클래스명.변수명  으로 사용 가능)
	
	private Scanner sc = new Scanner(System.in);
	private MemberService memberService = new MemberService();
	
	

	/**
	 * JDBC 프로젝트 메뉴 출력 View
	 */
	public void displayMenu() {
		
		int sel = 0;
		
		do {
			
			try {
				
				if(loginMember == null) { // 로그인 X
					
					System.out.println("==================");
					System.out.println("[로그인 화면]");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");
					System.out.println("==================");
					
					System.out.print("메뉴 선택 >> ");
					sel = sc.nextInt();
					sc.nextLine();
					
					switch(sel) {
					case 1: login();  break;
					case 2: signUp();  break;
					case 0: System.out.println("프로그램 종료"); break;
					default: System.out.println("잘못 입력하셨습니다.");
					}
					
				}else { // 로그인 O

					System.out.println("==================");
					System.out.println("[회원 전용 메뉴]");
					System.out.println("1. 내 정보 조회");
					System.out.println("2. 내 정보 수정");
					System.out.println("3. 비밀번호 수정");
					System.out.println("4. 회원 탈퇴");
					
					System.out.println("0. 로그아웃");
					System.out.println("==================");
					
					System.out.print("메뉴 선택 >> ");
					sel = sc.nextInt();
					sc.nextLine();
					
					switch(sel) {
					case 1 : selectMyInfo(); break;
					case 2 : updateMember(); break;
					case 3 : updatePw(); break;
					case 4 : secession(); break;
					case 0 : 
						loginMember = null; // 로그아웃
						System.out.println("로그아웃 되었습니다.");
						sel = -1; // 프로그램 종료 방지
						break;
					default : System.out.println("잘못 입력하셨습니다.");
					}
					
				}
				
			}catch (InputMismatchException e) {
				System.out.println("정수만 입력해주세요.");
				sel = -1;
				sc.nextLine();
			}
			
		}while(sel != 0);
		
	}
	
	
	// alt + shift + j : 클래스 또는 메소드에 설명을 다는 주석
	/**
	 * 회원 가입 View
	 */
	private void signUp() {
		System.out.println("[회원 가입]");
		
		// 아이디 중복 검사는 추후에 진행 예정
		System.out.print("아이디 : ");
		String memId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memPw = sc.next();
		
		System.out.print("이름 : ");
		String memNm = sc.next();
		
		System.out.print("전화번호 : ");
		String memPhone = sc.next();
		
		System.out.print("성별 (M/F) : ");
		char memGender = sc.next().toUpperCase().charAt(0);
		// String.toUpperCase() : 소문자 -> 대문자로 변경
		
		// 입력 받은 값을 하나의 Member VO 객체에 저장
		Member mem = new Member(memId, memPw, memNm, memPhone, memGender);
		
		try {
			int result = memberService.signUp(mem);

			if(result > 0) {
				System.out.println("회원 가입 성공!!");
			}else {
				System.out.println("회원 가입 실패...");
			}
			
			
		} catch (Exception e) {
			System.out.println("회원 가입 중 오류 발생.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 로그인 View
	 */
	private void login() {
		System.out.println("[로그인]");
		
		System.out.print("아이디 : ");
		String memId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memPw = sc.next();
		
		try {
			
			// DB에서 아이디, 비밀번호가 일치하는 회원 정보를 조회하는 Service 호출
			loginMember = memberService.login(memId, memPw);
			
			if(loginMember == null) { // 로그인 실패
				System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
			}else {
				System.out.println(loginMember.getMemNm() + "님 환영합니다." );
			}
			
			
		} catch (Exception e) {
			System.out.println("로그인 과정에서 오류 발생.");
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 내 정보 조회 View
	 */
	private void selectMyInfo() {
		
		System.out.println("[내 정보 조회]");
		
		System.out.println("아이디 : " + loginMember.getMemId());
		System.out.println("이름 : "   + loginMember.getMemNm());
		System.out.println("전화번호 : "+ loginMember.getMemPhone());
		System.out.println("성별 : "	 + loginMember.getMemGender());
		System.out.println("가입일 : " + loginMember.getJoinDate());
		
	}
	
	
	
	/**
	 * 회원 정보(이름, 전화번호) 수정 View
	 */
	private void updateMember() {
		System.out.println("[회원 정보 수정]");
		
		System.out.print("변경할 이름 : ");
		String updateName = sc.next();
		
		System.out.print("변경할 전화번호 : ");
		String updatePhone = sc.next();
		
		// 로그인한 회원의 이름, 전화번호를 변경
		try {
			int result = memberService.updateMember(loginMember.getMemNo(), 
													updateName, updatePhone);
			
			if(result > 0) {
				System.out.println("회원 정보 수정 성공!!!");
			}else {
				System.out.println("회원 정보 수정 실패 ...");
			}
			
		} catch (Exception e) {
			System.out.println("회원 정보 수정 중 오류 발생.");
			e.printStackTrace();
		}
		
		
	}
	
	
	/** 
	 * 비밀번호 변경 View
	 */
	private void updatePw() {
		System.out.println("[비밀번호 변경]");
		
		// 현재 비밀번호
		// 새 비밀번호
		// 새 비밀번호 확인
		System.out.print("현재 비밀번호 : ");
		String currPw = sc.next();
		
		System.out.print("새 비밀번호 : ");
		String newPw1 = sc.next();
		
		System.out.print("새 비밀번호 확인 : ");
		String newPw2 = sc.next();
		
		// 새 비밀번호, 확인 두 값이 같을 때만 비밀번호 변경 Service 호출
		if( newPw1.equals(newPw2) ) {
			
			try {
				int result = memberService.updatePw(currPw, newPw1);
				
				if(result > 0) {
					System.out.println("비밀번호 변경 성공.");
					
					loginMember = null; // 로그아웃
					System.out.println("다시 로그인 해주세요.");
					
				} else {
					System.out.println("비밀번호 변경 실패");
				}
				
			}catch (Exception e) {
				System.out.println("비밀번호 변경 중 오류 발생.");
				e.printStackTrace();
			}
			
			
		}else {
			System.out.println("새 비밀번호와, 새 비밀번호 확인이 일치하지 않습니다.");
		}
		
	}
	
	
	
	/**
	 * 회원 탈퇴 View
	 */
	private void secession() {
		System.out.println("[회원 탈퇴]");
		
		// 현재 비밀번호
		System.out.print("현재 비밀번호 입력 : ");
		String currPw = sc.next();
		
		// 정말 탈퇴하시겠습니까?
		System.out.print("정말 탈퇴하시겠습니까? (Y/N) : ");
		char ch = sc.next().toUpperCase().charAt(0);
		
		if(ch  == 'Y') { // 회원탈퇴
			
			try {
				int result = memberService.secession(currPw);
				
				if(result > 0) { // 탈퇴 성공
					System.out.println("탈퇴 성공 .... 안녕히 가세요.");
					loginMember = null; // 로그아웃
				}else {
					System.out.println("현재 비밀번호가 일치하지 않습니다.");
				}
				
				
			}catch (Exception e) {
				System.out.println("회원 탈퇴 중 오류 발생");
				e.printStackTrace();
			}
			
			
		}else if(ch == 'N') {
			System.out.println("회원 탈퇴 취소");
		}else {
			System.out.println("잘못 입력하셨습니다.");
		}
		
		
	}
	
	
	
	
	
	
}