package presentation.view;

import business.dto.User; 
import presentation.controller.UserController;
import presentation.view.UserRegView;
import util.ClearScreen;
import presentation.view.LoginView; 

import java.util.Scanner;

public class MainView {

    public static void main(String[] args) {
        MainView mainView = new MainView();
        UserController userController = new UserController(); // UserController 초기화
        int menu;

        do {
            mainView.printMainView();
            menu = mainView.selectMenu();

            switch (menu) {
                case 1: // 로그인
                    userController.handleLogin(); // 로그인 처리 호출
                    break;

                case 2: // 회원가입

                    userController.handleRegistration();
                    break;

                case 3: // 프로그램 종료
                    mainView.printExitMessage();
                    break;

                default:
                    CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
            }
        } while (menu != 3);
    }

    public void printMainView() {
    	System.out.println("");
    	ClearScreen.clear();
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                             [ B.O.A.T ]                                      |");
    	System.out.println("|                   Book of All Time : KOSTA Book System                       |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                        도서 대여 및 추천                                     |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println();
    	System.out.println("                        1. 로그인 | 2. 회원가입 | 3. 종료                       ");
    	System.out.println();
    	System.out.println("--------------------------------------------------------------------------------");

    }

    public int selectMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴 선택 > ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
        	ClearScreen.clear();
            System.out.println("숫자를 입력해주세요.");
            return -1; // 잘못된 입력 처리
        }
    }

    public void printExitMessage() {
    	System.out.println("");
    	ClearScreen.clear();
        System.out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다.");

    }
}