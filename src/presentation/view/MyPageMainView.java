package presentation.view;

import java.util.Scanner;

public class MyPageMainView {
    private final Scanner sc = new Scanner(System.in);

    public void display() {
        while (true) {
            System.out.println("=== 마이페이지 메인 ===");
            System.out.println("1. 내 정보 확인");
            System.out.println("2. 내 정보 수정");
            System.out.println("3. 관심분야 설정");
            System.out.println("4. 돌아가기");
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
                    // 컨트롤러를 통해 사용자 정보를 가져오도록 수정
                    new presentation.controller.MyPageController().handleViewUserData();
                    break;
                case 2:
                    new MyUserdataEditView().display();
                    break;
                case 3:
                    new SetBookCategoryView().display();
                    break;
                case 4:
                    System.out.println("마이페이지에서 나갑니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
