package business.service.user;

import business.dto.User;
import repository.dao.UserDao;
import repository.dao.CourseDao;
import business.dto.Course;

/**
 * 회원가입 기능 비즈니스 로직
 */
public class UserRegistration {

    private final UserDao userDao;
    private final CourseDao courseDao;

    // 생성자
    public UserRegistration(UserDao userDao, CourseDao courseDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }

    // 회원가입 메서드
    public boolean registerUser(User user) {
        // 1. 사용자 ID 중복 확인
        if (userDao.getUserById(user.getUserId()) != null) {
            System.out.println("이미 존재하는 사용자 ID입니다.");
            return false;
        }

        // 2. 비밀번호 유효성 검사 (예: 최소 4자 이상)
        if (!isValidPassword(user.getUserPassword())) {
            System.out.println("비밀번호가 유효하지 않습니다.");
            System.out.println("비밀번호는 최소 4자 이상이어야 합니다.");
            return false;
        }

        // 3. 과정 신청 가능 여부 확인
        if (!isCourseOpen(user.getCourseUid())) {
            System.out.println("선택한 과정은 신청이 불가능합니다.");
            return false;
        }

        // 4. 사용자 등록
        userDao.addUser(user);
        System.out.println("회원가입이 성공적으로 완료되었습니다."); // 성공 메시지 출력
        return true;
    }

    // 비밀번호 유효성 검사 메서드
    private boolean isValidPassword(String password) {
        // 예: 비밀번호는 최소 4자 이상이어야 함
        return password != null && password.length() >= 4;
    }

    // 과정 신청 가능 여부 확인 메서드
    private boolean isCourseOpen(int courseUid) {
        Course course = courseDao.getCourseById(courseUid);
        return course != null && course.getCourse_open() == 1;
    }
}
