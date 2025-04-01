package presentation.view;

import java.util.Scanner;

import exception.DmlException;
import presentation.controller.BookController;

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
                    new BookSearchView(new BookController()).display();
                    break;
                case 3:
                    displayBookManagementMenu(sc);
                    break;
                case 4:
                    new MypageView().display();
                    break;
                case 5:
                    System.out.println("로그아웃에 성공했습니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        } while (menu != 5);
    }

    private void printMenu() {
        System.out.println("[ 메인 화면 ]");
        System.out.println("===== 메뉴 선택 =====");
        System.out.println("1. 도서추천");
        System.out.println("2. 도서검색");
        System.out.println("3. 도서 대여/반납/예약");
        System.out.println("4. 마이페이지");
        System.out.println("5. 로그아웃");
    }

    private void displayBookManagementMenu(Scanner sc) {
        while (true) {
            System.out.println("\n===== 도서 관리 =====");
            System.out.println("1. 도서 대여");
            System.out.println("2. 도서 반납");
            System.out.println("3. 도서 예약");
            System.out.println("4. 이전 메뉴");
            System.out.print("메뉴 선택: ");
            
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        BookCartView bookCartView = new BookCartView();
                        bookCartView.display();
                        break;
                    case 2:
                        System.out.println("도서 반납 기능 실행");
                        BookReturnView bookReturnView = new BookReturnView();
                        bookReturnView.display();
                        break;
                    case 3:
                        System.out.println("도서 예약 기능 실행");
                        BookReservationView bookReservationView = new BookReservationView();
					try {
						bookReservationView.display();
					} catch (DmlException e) {
						System.out.println(e.getMessage());
					}
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("잘못된 선택입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
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