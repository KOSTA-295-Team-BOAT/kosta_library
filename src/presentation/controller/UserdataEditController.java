package presentation.controller;

import business.dto.User;
import business.service.book.UserdataEditService;
import presentation.view.MyUserdataEditView;

public class UserdataEditController {
    private final UserdataEditService service;
    private final MyUserdataEditView view;

    public UserdataEditController(MyUserdataEditView view) {
        this.service = new UserdataEditService();
        this.view = view;
    }

    public void editUserData(User user) {
        view.display(user); // View 호출
        service.updateUser(user); // Service를 통해 데이터베이스 업데이트
    }
}