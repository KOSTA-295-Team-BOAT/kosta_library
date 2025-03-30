package business.service.book;

import util.SessionManager;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
import business.dto.User;

public class UserdataEditService {
    private final UserDao userDao = new UserDaoImpl(); // UserDao 추가

    public void updateUserName(String newName) {
        String currentUserId = SessionManager.getCurrentUserId(); // 현재 사용자 ID 가져오기
        if (currentUserId != null) {
            User user = userDao.getUserById(currentUserId); // 사용자 정보 가져오기
            if (user != null) {
                user.setUserName(newName); // 이름 변경
                userDao.updateUser(user); // 데이터베이스에 업데이트
                System.out.println("사용자 이름이 '" + newName + "'(으)로 업데이트되었습니다.");
            }
        }
    }

    public void updateUserCategory(String newCategoryUid) {
        // 데이터베이스 로직으로 category_uid 업데이트
        System.out.println("사용자 관심분야가 UID '" + newCategoryUid + "'(으)로 업데이트되었습니다.");
        // ... 데이터베이스 업데이트 코드 ...
    }

    public void updateUser(User user) {
        userDao.updateUser(user); // 데이터베이스에 사용자 정보 업데이트
        System.out.println("사용자 정보가 업데이트되었습니다.");
    }
}