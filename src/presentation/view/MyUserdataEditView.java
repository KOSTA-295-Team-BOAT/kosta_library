package presentation.view;

import java.util.Scanner;
import business.dto.User;
import repository.dao.BookCategoryDao;
import repository.dao.BookCategoryDaoImpl;

public class MyUserdataEditView {
    private final BookCategoryDao bookCategoryDao = new BookCategoryDaoImpl(); // 카테고리 DAO 추가

    public void display(User user) {
        Scanner scanner = new Scanner(System.in);

        String userName = user.getUserName(); // DB에서 가져온 사용자 이름
        String categoryName = null;

        // category_uid를 이용해 category_name 가져오기
        if (user.getCategoryUid() != 0) {
            categoryName = bookCategoryDao.getCategoryById(user.getCategoryUid()).getCategoryName();
        }

        while (true) {
            System.out.println("=== 회원정보 수정 화면 ===");
            System.out.println("나의 이름: " + userName); // 사용자 이름 출력
            System.out.println("관심분야: " + (categoryName != null ? categoryName : "설정되지 않음"));
            System.out.println("1. 이름 변경");
            System.out.println("2. 관심분야 변경");
            System.out.println("3. 돌아가기");
            System.out.print("메뉴 선택: ");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("변경할 이름을 입력하세요: ");
                    String newName = scanner.nextLine();
                    user.setUserName(newName); // 사용자 객체에 이름 설정
                    return; // 변경된 이름 반환
                case 2:
                    System.out.print("변경할 관심분야 코드를 입력하세요: ");
                    String newCategory = scanner.nextLine();
                    user.setCategoryUid(Integer.parseInt(newCategory)); // 사용자 객체에 관심분야 설정
                    return; // 변경된 관심분야 반환
                case 3:
                    System.out.println("회원정보 수정 화면을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
