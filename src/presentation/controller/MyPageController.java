package presentation.controller;

import business.dto.User;
import presentation.view.MyPageMainView;
import presentation.view.MyUserdataView;
import util.SessionManager;
import repository.dao.BookCategoryDao;
import repository.dao.BookCategoryDaoImpl;

public class MyPageController {
    private final BookCategoryDao bookCategoryDao = new BookCategoryDaoImpl();

    public void handleMyPage() {
        new MyPageMainView().display();
    }

    public void handleViewUserData() {
        String currentUserId = SessionManager.getCurrentUserId();
        if (currentUserId != null) {
            User currentUser = new UserController().getUserById(currentUserId); // DB에서 사용자 정보 가져오기
            if (currentUser != null) {
                String categoryName = null;
                if (currentUser.getCategoryUid() != 0) {
                    categoryName = bookCategoryDao.getCategoryById(currentUser.getCategoryUid()).getCategoryName();
                }
                new MyUserdataView().display(currentUser, categoryName);
            } else {
                System.out.println("사용자 정보를 불러올 수 없습니다.");
            }
        } else {
            System.out.println("로그인 상태를 확인해주세요.");
        }
    }
}
