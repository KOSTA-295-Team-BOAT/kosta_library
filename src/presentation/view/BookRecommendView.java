package presentation.view;

import business.dto.User;
import presentation.view.CourseBookRecommendView;
import presentation.view.FavoriteBookRecommendView;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
import util.SessionManager;

public class BookRecommendView {
    private final UserDao userDao = new UserDaoImpl();
    private final FavoriteBookRecommendView favoriteView = new FavoriteBookRecommendView();
    private final CourseBookRecommendView courseView = new CourseBookRecommendView();

    public void display() {
        System.out.println("===== 도서 추천 시스템 =====");
        
        // 현재 로그인한 사용자 정보 가져오기
        String userId = SessionManager.getCurrentUserId();
        User user = userDao.getUserById(userId);

        // 사용자의 관심분야 존재 여부 확인
        if (user.getFavoriteCategories() != null && !user.getFavoriteCategories().isEmpty()) {
            System.out.println("관심분야 기반 추천을 시작합니다...");
            favoriteView.display(userId);
        } else {
            System.out.println("과정 기반 추천을 시작합니다...");
            courseView.display(userId);
        }
    }
}