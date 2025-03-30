package business.service.user;

import java.util.List;

import business.dto.User;
import repository.dao.UserDao;

/**
 * 사용자 정보와 관련된 비즈니스 로직을 담당하는 클래스
 * @author 황태윤
 * @since 2025.03.28
 */
public class UserService {

    private final UserDao userDao;

    // 생성자
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 사용자 추가
    public void addUser(User user) {
        // user_name 유효성 검사 추가 가능
        userDao.addUser(user);
    }

    // 사용자 ID로 조회
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        // 비즈니스 로직 추가 가능 (예: 결과 정렬)
        return userDao.getAllUsers();
    }

    // 사용자 업데이트
    public void updateUser(User user) {
        // user_name 유효성 검사 추가 가능
        userDao.updateUser(user);
    }

    // 로그인 검증
    public boolean isValidUser(String userId, String password) {
        return userDao.isValidUser(userId, password);
    }
}
