package presentation.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import business.dto.User;
import business.service.book.UserdataEditService; // UserdataEditService import 추가
import repository.dao.BookCategoryDao;
import repository.dao.BookCategoryDaoImpl;

public class MyUserdataEditView {
    private final BookCategoryDao bookCategoryDao = new BookCategoryDaoImpl(); // 카테고리 DAO 추가

    public void display(User user) {
        Scanner scanner = new Scanner(System.in);

        String userName = user.getUserName(); // DB에서 가져온 사용자 이름
        List<Integer> favoriteCategories = user.getFavoriteCategories(); // 관심 카테고리 ID 목록
        List<String> favoriteCategoryNames = new ArrayList<>(); // 관심 카테고리 이름 목록

        // 관심 카테고리 이름 가져오기
        if (favoriteCategories != null) {
            for (int categoryUid : favoriteCategories) {
                String categoryName = bookCategoryDao.getCategoryById(categoryUid).getCategoryName();
                if (categoryName != null) {
                    favoriteCategoryNames.add(categoryName);
                }
            }
        }

        while (true) {
            System.out.println("=== 회원정보 수정 화면 ===");
            System.out.println("나의 이름: " + userName); // 사용자 이름 출력
            System.out.println("관심분야: " + (favoriteCategoryNames.isEmpty() ? "설정되지 않음" : String.join(", ", favoriteCategoryNames)));
            System.out.println("1. 이름 변경");
            System.out.println("2. 관심분야 변경");
            System.out.println("3. 관심분야 삭제");
            System.out.println("4. 돌아가기");
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
                    System.out.println("====== 관심 카테고리 변경 ======");
                    System.out.println("현재 관심 카테고리: " + (favoriteCategoryNames.isEmpty() ? "없음" : String.join(", ", favoriteCategoryNames)));
                    System.out.println("사용 가능한 카테고리 목록:");
                    bookCategoryDao.getAllCategories().forEach(category -> 
                        System.out.println("ID: " + category.getCategoryUid() + ", 이름: " + category.getCategoryName())
                    );

                    // 기존 관심 카테고리 ID 조회
                    Set<Integer> existingCategoryIds = new HashSet<>(favoriteCategories != null ? favoriteCategories : new ArrayList<>());

                    System.out.println("추가할 관심 카테고리 ID를 쉼표로 구분하여 입력하세요 (예: 1,2,3): ");
                    String[] inputCategories = scanner.nextLine().split(",");
                    Set<Integer> newCategoryIds = new HashSet<>();

                    // 중복 체크 및 새로운 카테고리 추가
                    for (String categoryId : inputCategories) {
                        try {
                            int categoryUid = Integer.parseInt(categoryId.trim());
                            if (!existingCategoryIds.contains(categoryUid)) {
                                newCategoryIds.add(categoryUid);
                            } else {
                                System.out.println("⚠ 이미 등록된 카테고리 ID " + categoryUid + "는 제외됩니다.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ 숫자 형식이 잘못된 입력: " + categoryId);
                        }
                    }

                    // 새로운 카테고리만 DB에 추가
                    for (int categoryUid : newCategoryIds) {
                        new UserdataEditService().addUserFavoriteCategory(user.getUserId(), categoryUid);
                        String categoryName = bookCategoryDao.getCategoryById(categoryUid).getCategoryName();
                        if (categoryName != null && !favoriteCategoryNames.contains(categoryName)) {
                            favoriteCategoryNames.add(categoryName);
                            favoriteCategories.add(categoryUid);
                        }
                    }
                    break;
                case 3:
                System.out.println("====== 관심 카테고리 삭제 ======.");
                    if (favoriteCategories.isEmpty()) {
                        System.out.println("삭제할 관심분야가 없습니다.");
                        break;
                    }
                    System.out.println("현재 관심 카테고리:");
                    for (int i = 0; i < favoriteCategories.size(); i++) {
                        System.out.println("ID: " + favoriteCategories.get(i) + ", 이름: " + favoriteCategoryNames.get(i));
                    }
                    System.out.print("삭제할 관심 카테고리 ID를 입력하세요: ");
                    String deleteInput = scanner.nextLine();
                    try {
                        int deleteCategoryUid = Integer.parseInt(deleteInput.trim());
                        if (favoriteCategories.contains(deleteCategoryUid)) {
                            new UserdataEditService().deleteUserFavoriteCategory(user.getUserId(), deleteCategoryUid);
                            int index = favoriteCategories.indexOf(deleteCategoryUid);
                            favoriteCategories.remove(index);
                            favoriteCategoryNames.remove(index);
                            System.out.println("관심 카테고리가 삭제되었습니다.");
                        } else {
                            System.out.println("입력한 ID가 관심 카테고리에 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("잘못된 입력입니다.");
                    }
                    break;
                case 4:
                    System.out.println("회원정보 수정 화면을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
