package business.service.user;

import business.dto.User;
import repository.dao.UserDao;

/**
 * 회원가입 기능 비즈니스 로직
 */
public class UserRegistration {

    private final UserDao userDao;

    // 생성자
    public UserRegistration(UserDao userDao) {
        this.userDao = userDao;
    }

    // 회원가입 메서드
    public boolean registerUser(User user) {
        // 1. 사용자 ID 중복 확인
        if (userDao.getUserById(user.getUserId()) != null) {
            System.out.println("이미 존재하는 사용자 ID입니다.");
            return false;
        }

        // 2. 비밀번호 유효성 검사 (예: 최소 길이, 특수 문자 포함 여부 등)
        if (!isValidPassword(user.getUserPassword())) {
            System.out.println("비밀번호가 유효하지 않습니다.");
            return false;
        }

        // 3. 사용자 등록
        userDao.addUser(user);
        System.out.println("회원가입이 성공적으로 완료되었습니다.");
        return true;
    }

    // 비밀번호 유효성 검사 메서드
    private boolean isValidPassword(String password) {
        // 예: 비밀번호는 최소 8자 이상이어야 함
        return password != null && password.length() >= 8;
    }
}
