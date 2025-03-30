package presentation.view;

import java.util.Scanner;
import business.dto.User;
import util.SessionManager;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
import presentation.controller.UserdataEditController;

public class MyPageMainView {
    private final Scanner sc = new Scanner(System.in);
    private final UserDao userDao = new UserDaoImpl(); // UserDao 추가
    private String userName = "기본 사용자 이름"; // 사용자 이름 초기값
    private String categoryName = "기본 관심분야"; // 관심분야 초기값

    public void display() {
        while (true) {
            System.out.println("=== 마이페이지 메인 ===");
            System.out.println("1. 내 정보 확인");
            System.out.println("2. 내 정보 수정");
            System.out.println("3. 돌아가기");
            System.out.print("메뉴 선택: ");
            
            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    // 내 정보 확인
                    new presentation.controller.MyPageController().handleViewUserData();
                    break;
                case 2:
                    // 내 정보 수정
                    String currentUserId = SessionManager.getCurrentUserId(); // 현재 사용자 ID 가져오기
                    if (currentUserId != null) {
                        User user = userDao.getUserById(currentUserId); // 최신 사용자 정보 가져오기
                        if (user != null) {
                            MyUserdataEditView myUserdataEditView = new MyUserdataEditView(); // 뷰 생성
                            UserdataEditController userdataEditController = new UserdataEditController(myUserdataEditView); // 컨트롤러 생성
                            userdataEditController.editUserData(user); // 수정 화면 호출
                        } else {
                            System.out.println("사용자 정보를 불러올 수 없습니다.");
                        }
                    } else {
                        System.out.println("로그인 상태를 확인해주세요.");
                    }
                    break;
                case 3:
                    System.out.println("마이페이지에서 나갑니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
