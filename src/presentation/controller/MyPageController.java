package presentation.controller;

import business.dto.User;
import presentation.view.MyPageMainView;
import presentation.view.MyUserdataView;
import util.SessionManager;

public class MyPageController {
    public void handleMyPage() {
        new MyPageMainView().display();
    }

    public void handleViewUserData() {
        String currentUserId = SessionManager.getCurrentUserId();
        if (currentUserId != null) {
            User currentUser = new presentation.controller.UserController().getUserById(currentUserId);
            if (currentUser != null) {
                new MyUserdataView().display(currentUser);
            } else {
                System.out.println("사용자 정보를 불러올 수 없습니다.");
            }
        } else {
            System.out.println("로그인 상태를 확인해주세요.");
        }
    }
}
