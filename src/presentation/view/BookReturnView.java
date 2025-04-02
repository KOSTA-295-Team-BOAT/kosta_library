package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.RentDetail;
import presentation.controller.BookController;
import util.SessionManager;

/**
 * 도서 반납 화면
 */
public class BookReturnView {

	BookController controller = new BookController();
	Scanner sc = new Scanner(System.in);

	public void display() {
		System.out.println("도서 반납 화면");
		Scanner sc = new Scanner(System.in);
		System.out.println("---------------------------------------------------------------------------------");
		List<RentDetail> rentDetailList = controller.getRentDetailByUserId(SessionManager.getCurrentUserId());
		if (!rentDetailList.isEmpty()) {
			int i = 1;
			for (RentDetail detail : rentDetailList) {
				System.out.println("[" + (i++) + "]" + " | " + detail.getRentReturnDue() + " 까지 + "
						+ controller.getBookById(detail.getBookUid()).getBookName());
			}
		}
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("반납할 도서 번호를 선택하세요");
		int selectNumber = -1;
		RentDetail returnInfo = null;
		try {
			selectNumber = sc.nextInt();
			if (selectNumber < 1 || selectNumber > rentDetailList.size())
				throw new Exception();
			returnInfo = controller.returnOneBook(rentDetailList.get(selectNumber - 1).getRentDetailUid());
			System.out.println(controller.getBookById(returnInfo.getBookUid()).getBookName() + "도서를 반납합니다.");
		} catch (Exception e) {
			CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
		}
	}
}
