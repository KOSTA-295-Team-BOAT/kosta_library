package presentation.view;

import java.util.Scanner;

import util.ClearScreen;

/**
 * 메인뷰에서 로그인 부분을 출력해주는 클래스
 * @author 
 */
public class LoginView {
    public String[] inputLoginInfo() {
    	printLoginView();
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID를 입력하세요: ");
        String id = scanner.nextLine();
        System.out.print("패스워드를 입력하세요: ");
        String password = scanner.nextLine();
        return new String[]{id, password};
    }

    public void printLoginSuccess() {
    	System.out.println("");
    	System.out.println("로그인 성공! 메인 화면으로 이동합니다.");

    }

    public void printLoginFail() {
        System.out.println("로그인 실패! ID 또는 비밀번호를 확인해주세요.");
    }
	public void printLoginView() {
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
    	System.out.println("|                                                                              |");
    	System.out.println("|            시스템에 로그인합니다. ID와 패스워드를 입력해주세요.              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                                                                                ");
	}
    
}
