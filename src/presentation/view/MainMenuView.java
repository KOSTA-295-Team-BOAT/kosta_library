package presentation.view;

import java.util.Scanner;

public class MainMenuView {

	public void display() {
		Scanner sc = new Scanner(System.in);
		int menu;
		do {
			printMenu();
			menu = getMenu(sc);
			switch (menu) {
				case 1:
					new BookRecommendView().display();
					break;
				case 2:
					new BookSerachView().display();
					break;
				case 3:
					new MypageView().display();
					break;
				case 4:
					System.out.println("로그아웃에 성공했습니다.");
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
			}
		} while (menu != 4);
	}

	private void printMenu() {
		System.out.println("[ 메인 화면 ]");
		System.out.println("===== 메뉴 선택 =====");
		System.out.println("1. 도서추천");
		System.out.println("2. 도서검색");
		System.out.println("3. 마이페이지");
		System.out.println("4. 로그아웃");
	}

	private int getMenu(Scanner sc) {
		System.out.print("메뉴 선택: ");
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주세요.");
			return -1;
		}
	}
}