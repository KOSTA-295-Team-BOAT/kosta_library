package presentation.view;

import java.util.Scanner;

/**
 *@author 
 *프로젝트 실행을 위한 메인 메소드 클래스
 */
public class MainView {

	public static void main(String[] args) {

		// 메인뷰 객체 생성
		MainView mainView = new MainView();
		int menu;

		do {
			// 메인뷰 출력
			mainView.printMainView();
			// 메뉴 선택
			menu = mainView.selectMenu();

			switch (menu) {
			case 1: // 로그인
				LoginView loginView = new LoginView();
				loginView.printLoginView();
				if (loginView.inputLoginInfo()) {
					System.out.println("로그인 성공!");
				} else {
					loginView.printLoginView();
				}
				break;

			case 2: // 회원가입
				UserRegView userRegView = new UserRegView();
				userRegView.printUserRegView();
				if (userRegView.inputUserRegInfo()) {
					userRegView.printUserRegSuccess();
				} else {
					userRegView.printUserRegFail();
				}
				break;

			case 3: // 프로그램 종료
				mainView.printExitMessage();
				break;

			default:
				System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
			}
		} while (menu != 3);
	}

	public void printMainView() {
		System.out.println("[ B.O.A.T ]");
		System.out.println("===== 메인 메뉴 =====");
		System.out.print("1. 로그인");
		System.out.print(" 2. 회원가입");
		System.out.println(" 3. 종료");
	}

	public int selectMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.print("메뉴 선택: ");
		int choice = sc.nextInt();
		sc.nextLine(); // 버퍼 비우기
		return choice;
	}

	public void printExitMessage() {
		System.out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다.");
	}

}
