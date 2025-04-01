package presentation.view;

import java.util.Scanner;

import presentation.controller.BookController;
import util.ClearScreen;

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
                    System.out.println("시스템에서 로그아웃합니다.");
    				System.out.println("엔터를 누르면 진행합니다 ...");
    				new Scanner(System.in).nextLine();
                    return;
                default:
                	CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
            }
        } while (menu != 5);
    }

    private void printMenu() {
    	System.out.println("");
    	ClearScreen.clear();
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                             [ B.O.A.T ]                                      |");
    	System.out.println("|                   Book of All Time : KOSTA Book System                       |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                       실행할 기능을 선택해 주세요                            |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                                                                                ");
        System.out.println("1. 도서추천 | 2. 도서검색 | 3. 대여 / 반납 / 예약 | 4. 마이페이지 | 5. 로그아웃 ");
        System.out.println();
		System.out.println("--------------------------------------------------------------------------------");
//        System.out.print("메뉴 선택 >");        
    }

    private void displayBookManagementMenu(Scanner sc) {
        while (true) {
        	System.out.println("--------------------------------------------------------------------------------");
        	System.out.println("|                                                                              |");
        	System.out.println("|                                                                              |");
        	System.out.println("|                                                                              |");
        	System.out.println("|                             [ B.O.A.T ]                                      |");
        	System.out.println("|                   Book of All Time : KOSTA Book System                       |");
        	System.out.println("|                                                                              |");
        	System.out.println("|                                                                              |");
        	System.out.println("|                       도서 대여 / 반납 / 예약 메뉴                           |");
        	System.out.println("|                                                                              |");
        	System.out.println("|                                                                              |");
        	System.out.println("|                                                                              |");
        	System.out.println("--------------------------------------------------------------------------------");
        	System.out.println();
        	System.out.println("1. 북카트 보기 | 2. 도서 반납 | 3. 도서 예약 | 4. 이전 메뉴");
            System.out.println();
    		System.out.println("--------------------------------------------------------------------------------");
//            System.out.print("메뉴 선택 >");
            
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
                        bookReservationView.display();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("잘못된 입력입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private int getMenu(Scanner sc) {
        System.out.print("메뉴 선택 > ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
            return -1;
        }
    }
}