package presentation.view;

import java.util.Scanner;

/**
 * 메인뷰에서 로그인 부분을 출력해주는 클래스
 * @author 
 */
public class LoginView {

	public void printLoginView() {
		System.out.println("===== 로그인 화면 =====");
		System.out.println("1. 로그인");
		System.out.println("2. 돌아가기");
		System.out.print("메뉴를 선택하세요: ");
	}

	public boolean inputLoginInfo() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("ID를 입력하세요: ");
		String id = scanner.nextLine();
		System.out.print("패스워드를 입력하세요: ");
		String password = scanner.nextLine();

		// user 테이블 조회 및 id, 패스워드 비교 (임시 로직)
		if ("admin".equals(id) && "1234".equals(password)) {
			System.out.println("로그인 성공! 메인 화면으로 이동합니다.");
			return true;
		} else {
			printLoginFailView();
			return false;
		}
	}

	public void printLoginFailView() {
		System.out.println("로그인이 실패하였습니다.");
		System.out.println("시작 화면으로 복귀합니다.");
	}

	public void printLogoutSuccessView() {
		System.out.println("로그아웃에 성공하였습니다.");
		System.out.println("시작 화면으로 복귀합니다.");
	}
}
