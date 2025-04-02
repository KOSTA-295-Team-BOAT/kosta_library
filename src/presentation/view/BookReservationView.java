package presentation.view;

import java.util.List;

import business.dto.Book;
import business.dto.BookReservation;
import presentation.controller.BookController;
import repository.dao.BookReservationDao;
import repository.dao.BookReservationDaoImpl;
import util.ClearScreen;
import util.SessionManager;

public class BookReservationView {

	BookController controller = new BookController();

	public void display() {
		String userId = SessionManager.getCurrentUserId();
		List<BookReservation> reservations = controller.getActiveReservationsByUserId(userId);

//		System.out.println("\n===== 나의 예약 도서 목록 =====");
		displayMenu();

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println();
		if (reservations.isEmpty()) {
			System.out.println("현재 예약 중인 도서가 없습니다.");
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------");
			return;
		}
		for (int i = 0; i < reservations.size(); i++) {
			BookReservation res = reservations.get(i);
			Book book = controller.getBookById(res.getBookUid());
			System.out.printf("[%d] %s | 예약일: %s | 만료일: %s\n", i + 1, book.getBookName(), res.getReservationDate(),
					res.getReservationDue());
		}
		System.out.println();
    	System.out.println("--------------------------------------------------------------------------------");

	}
    void displayMenu() {
    	System.out.println("");
    	ClearScreen.clear();
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                             [ B.O.A.T ]                                      |");
    	System.out.println("|                   Book of All Time : KOSTA Book System                       |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                       나의 예약 도서 목록                                    |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println();
    }
	
}
