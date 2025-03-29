package presentation.controller;

import business.dto.User;
import business.service.user.UserService;
import business.service.user.UserRegistration;
import repository.dao.UserDaoImpl;

import java.util.List;

/**
 * 기능별 상세 컨트롤러 : 사용자 관련 컨트롤러 클래스
 */
public class UserController {

    private final UserService userService;
    private final UserRegistration userRegistration; // 추가된 필드

    // 기본 생성자 추가
    public UserController() {
        this.userService = new UserService(new UserDaoImpl());
        this.userRegistration = new UserRegistration(new UserDaoImpl());
    }

    // 수정된 생성자: 기존 UserService와 함께 UserRegistration도 초기화
    public UserController(UserService userService) {
        this.userService = userService;
        this.userRegistration = new UserRegistration(new UserDaoImpl());
    }
    
    // 신규 메서드: 회원가입 기능을 위한 메서드 추가
    public boolean registerUser(business.dto.User user) {
        return userRegistration.registerUser(user);
    }

    // 사용자 추가
    public void addUser(User user) {
        userService.addUser(user);
        System.out.println("사용자가 성공적으로 추가되었습니다.");
    }

    // 사용자 ID로 조회
    public User getUserById(String userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            System.out.println("사용자 정보: " + user);
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("모든 사용자 목록: " + users);
        return users;
    }

    // 사용자 업데이트
    public void updateUser(User user) {
        userService.updateUser(user);
        System.out.println("사용자 정보가 성공적으로 업데이트되었습니다.");
    }

    // 사용자 삭제
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
        System.out.println("사용자가 성공적으로 삭제되었습니다.");
    }
}
