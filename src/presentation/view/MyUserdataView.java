package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.BookReservation;
import business.dto.RentDetail;
import business.dto.User;
import presentation.controller.BookController;
import util.SessionManager;

public class MyUserdataView {
    // 컨트롤러에서 전달받은 User 객체를 이용하여 뷰를 출력
    BookController controller = new BookController();
	public void display(User user, String categoryName) {
        // DB에서 조회한 데이터 사용: 아이디와 독서 점수는 실제 DB 값, 이름은 DB에서 가져온 값
        String id = user.getUserId();
        int score = user.getUserScore();
        String name = user.getUserName();

        // 독서 등급 결정: 30점 이상 골드, 20점 이상 실버, 10점 이상 브론즈, 그 미만은 일반
        String grade;
        if (score >= 30) {
            grade = "골드";
        } else if (score >= 20) {
            grade = "실버";
        } else if (score >= 10) {
            grade = "브론즈";
        } else {
            grade = "일반";
        }

        // 화면 출력 (내부 식별자 용어는 노출되지 않음)
        System.out.println("=== 내 정보 확인 ===");
        System.out.println("아이디: " + id);
        System.out.println("이름: " + name); 
        System.out.println("독서 등급: " + grade);
        System.out.println("관심분야: " + (categoryName != null ? categoryName : "설정되지 않음"));

        System.out.println("대여중인 책: ");
        System.out.println("---------------------------------------------------------------------------------");
        List<RentDetail> rentDetailList = controller.getRentDetailByUserId(SessionManager.getCurrentUserId());
        if (!rentDetailList.isEmpty()) {
        	for(RentDetail detail : rentDetailList) {
        		System.out.println(controller.getBookById(detail.getBookUid()).getBookName() + " | " + detail.getRentReturnDue()+" 까지");
        	}
        }
        System.out.println("---------------------------------------------------------------------------------");        
        
        // TODO: 내가 예약중인 책 목록 기능 구현 필요 (List<Book> myReservationBookList)
        System.out.println("예약중인 책:");
        List<BookReservation> reservationList = controller.getActiveReservationsByUserId(SessionManager.getCurrentUserId());
        if (!reservationList.isEmpty()) {
            for (BookReservation res : reservationList) {
                String bookName = controller.getBookById(res.getBookUid()).getBookName();
                System.out.println(bookName + " | 예약일: " + res.getReservationDate() + " | 만료일: " + res.getReservationDue());
            }
        } else {
            System.out.println("예약 중인 책이 없습니다.");
        }
        
        System.out.print("돌아가시려면 엔터를 누르세요...");
        new Scanner(System.in).nextLine();

        // 마이페이지 메인 화면으로 돌아가기
        //new MyPageMainView().display();
        return;
    }
}
