package presentation.controller;

import java.util.ArrayList;
import java.util.List;

import business.dto.User;
import exception.SearchWrongException;
import presentation.view.MyPageMainView;
import presentation.view.MyUserdataView;
import repository.dao.BookCategoryDao;
import repository.dao.BookCategoryDaoImpl;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
import util.SessionManager;

public class MyPageController {
    private final BookCategoryDao bookCategoryDao = new BookCategoryDaoImpl();
    private final UserDao userDao = new UserDaoImpl(); 

    public void handleMyPage() {
        MyPageMainView myPageMainView = new MyPageMainView(); // 마이페이지 메인 뷰 생성
        myPageMainView.display(); // 마이페이지 메인 뷰 출력
        
    }

    public void handleViewUserData() throws SearchWrongException {
        String currentUserId = SessionManager.getCurrentUserId();
        if (currentUserId != null) {
            User currentUser = new UserController().getUserById(currentUserId); // DB에서 사용자 정보 가져오기
            if (currentUser != null) {
                List<Integer> favoriteCategories = new UserDaoImpl().getUserFavoriteCategories(currentUserId); // 관심 카테고리 ID 가져오기
                List<String> favoriteCategoryNames = new ArrayList<>();
                for (int categoryUid : favoriteCategories) {
                    String categoryName = bookCategoryDao.getCategoryById(categoryUid).getCategoryName();
                    if (categoryName != null) {
                        favoriteCategoryNames.add(categoryName);
                    }
                }
                String categoryNames = favoriteCategoryNames.isEmpty() ? "설정되지 않음" : String.join(", ", favoriteCategoryNames);
                new MyUserdataView().display(currentUser, categoryNames);
            } else {
                System.out.println("사용자 정보를 불러올 수 없습니다.");
            }
        } else {
            System.out.println("로그인 상태를 확인해주세요.");
        }
    }
}
