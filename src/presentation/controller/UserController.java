package presentation.controller;

import business.dto.User;
import business.service.user.UserRegistration;
import business.service.user.UserService;
import presentation.view.LoginView;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
import repository.dao.CourseDaoImpl;
import util.SessionManager;

/**
 * 기능별 상세 컨트롤러 : 사용자 관련 컨트롤러 클래스
 */
public class UserController {

    private final UserService userService;
    private final UserRegistration userRegistration;
    private final LoginView loginView;
	private UserDao userDao;

    // 기본 생성자 수정: 모든 필드 초기화
    public UserController() {
        this.userService = new UserService(new UserDaoImpl());
        this.userRegistration = new UserRegistration(new UserDaoImpl(), new CourseDaoImpl());
        this.loginView = new LoginView();
    }

    // 수정된 생성자: 기존 UserService와 함께 UserRegistration도 초기화
    public UserController(UserService userService) {
        this.userService = userService;
        this.userRegistration = new UserRegistration(new UserDaoImpl(), new CourseDaoImpl());
        this.loginView = new LoginView();
    }

    // 로그인 및 사용자 관리 기능
    public UserController(LoginView loginView, UserDao userDao) {
        this.loginView = loginView;
        this.userDao = userDao;
        this.userService = new UserService(new UserDaoImpl()); 
        // 수정: UserRegistration 생성자에 CourseDaoImpl 추가
        this.userRegistration = new UserRegistration(new UserDaoImpl(), new CourseDaoImpl());
    }
    
    //회원가입 기능을 위한 메서드 추가
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
        if (user == null) {
            System.out.println("사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    // 현재 사용자 정보를 반환
    public business.dto.User getCurrentUser() {
        // SessionManager에서 현재 로그인한 사용자 ID를 가져옴
        String currentUserId = SessionManager.getCurrentUserId();
        if (currentUserId != null) {
            // DB에서 해당 사용자 정보를 조회
            User user = userService.getUserById(currentUserId);
            if (user instanceof business.dto.User currentUser) {
                // 필요한 필드: user_id, user_score, user_name 반환
                return new business.dto.User(
                    currentUser.getUserId(),
                    "",
                    0,
                    0,
                    0,
                    currentUser.getUserScore(),
                    currentUser.getUserName()
                );
            }
        }
        return null;
    }

    // 사용자 업데이트
    public void updateUser(User user) {
        userService.updateUser(user);
        System.out.println("사용자 정보가 성공적으로 업데이트되었습니다.");
    }

    // 로그인 처리
    public void handleLogin() {
        String[] credentials = loginView.inputLoginInfo();
        String id = credentials[0];
        String password = credentials[1];

        if (userService.isValidUser(id, password)) {
            // 로그인 성공 시 세션에 사용자 ID 저장
            SessionManager.setCurrentUserId(id);
            loginView.printLoginSuccess();
            new presentation.view.MainMenuView().display();
        } else {
            loginView.printLoginFail();
        }
    }
    
    // 회원가입 후 로그인 화면으로 전환하는 메서드 
    public void handleRegistration() {
        presentation.view.UserRegView userRegView = new presentation.view.UserRegView();
        userRegView.printUserRegView();
        business.dto.User user = userRegView.inputUserRegInfo();
        if (user != null) {
            if (registerUser(user)) {
                userRegView.printUserRegSuccess();
                // 자동으로 로그인 화면으로 전환
                handleLogin();
            } else {
                userRegView.printUserRegCancel();
            }
        } else {
            userRegView.printUserRegCancel();
        }
    }
}
