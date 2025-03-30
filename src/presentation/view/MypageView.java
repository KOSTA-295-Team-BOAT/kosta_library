package presentation.view;

import presentation.controller.MyPageController;

public class MypageView {
    public void display() {
        new MyPageController().handleMyPage();
    }
}