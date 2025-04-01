package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.Book;
import business.dto.BookReservation;
import exception.DmlException;
import presentation.controller.BookController;
import repository.dao.BookReservationDao;
import repository.dao.BookReservationDaoImpl;
import util.SessionManager;

public class BookReservationView {

	BookController controller = new BookController();
	Scanner scanner = new Scanner(System.in);

	public void display() throws DmlException {
		String userId = SessionManager.getCurrentUserId();
		List<BookReservation> reservations = controller.getActiveReservationsByUserId(userId);

		System.out.println("\n===== 나의 예약 도서 목록 =====");

		if (reservations.isEmpty()) {
			System.out.println("현재 예약 중인 도서가 없습니다.");
			return;
		}

		for (int i = 0; i < reservations.size(); i++) {
			BookReservation res = reservations.get(i);
			Book book = controller.getBookById(res.getBookUid());

			String rentStatus = (book.getBookStatus() == 0) ? "대여 가능" : "대여 중";

			System.out.printf("[%d] %s | 예약일: %s | 만료일: %s (%s)\n ", i + 1, book.getBookName(),
					res.getReservationDate(), res.getReservationDue(), rentStatus);
		}

		System.out.println("\n원하는 도서 번호를 입력하세요 (b: 돌아가기): ");
		String input = scanner.nextLine().trim();

		if (input.equalsIgnoreCase("b")) {
			return;
		}

		try {
			int selection = Integer.parseInt(input);
			if (selection < 1 || selection > reservations.size()) {
				System.out.println("잘못된 번호입니다.");
				return;
			}

			BookReservation selectedReservation = reservations.get(selection - 1);
			int bookUid = selectedReservation.getBookUid();

			boolean success = controller.rentBook(userId, bookUid);
			if (success) {
				System.out.println("도서 대여가 완료되었습니다.");
			} else {
				System.out.println("대여 불가능한 상태입니다.");
			}

		} catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다. 숫자나 b를 입력하세요.");
		}

	}
}
