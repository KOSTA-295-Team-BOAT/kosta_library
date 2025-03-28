package presentation.view;

import java.util.Scanner;
import business.service.user.UserRegistration;
import repository.dao.UserDao;
import presentation.model.UserDaoImpl; // 올바른 패키지 경로로 수정
import business.dto.User;

/**
 * 프로젝트 실행을 위한 메인 메소드 클래스
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
                        System.out.println("로그인 실패! 다시 시도해주세요.");
                    }
                    break;

                case 2: // 회원가입
                    UserRegView userRegView = new UserRegView();
                    userRegView.printUserRegView(); // 회원가입 화면 출력
                    UserDao userDao = new UserDaoImpl();
                    UserRegistration userRegistration = new UserRegistration(userDao);// 서비스랑 dao는 뷰가 몰라야되고 컨트롤러에서 맡겨야 한다.
                    User user = userRegView.inputUserRegInfo();
                    if (user != null && userRegistration.registerUser(user)) {
                        // 성공 메시지 출력 제거
                    } else {
                        userRegView.printUserRegCancel();
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
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");
    }

    public int selectMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴 선택: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
            return -1; // 잘못된 입력 처리
        }
    }

    public void printExitMessage() {
        System.out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다.");
    }
}