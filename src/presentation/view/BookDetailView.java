package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.Book;
import business.dto.BookReservation;
import business.dto.User;
import business.service.book.BookRentService;
import business.service.book.BookReservationService;
import exception.DmlException;
import presentation.controller.BookCartController;
import presentation.controller.BookController;
import util.SessionManager;

public class BookDetailView {
	private final Scanner scanner = new Scanner(System.in);

	private final BookRentService rentService = new BookRentService();
	private final BookReservationService reservationService = new BookReservationService();

	public Book display(Book book) {
		BookController controller = new BookController();
		while (true) {
			book = controller.getBookById(book.getBookUid());
			System.out.println("\n===== 도서 상세 정보 =====");
			System.out.println("도서명: " + book.getBookName());
			System.out.println("저자: " + book.getBookAuthor());
			System.out.println("출판사: " + book.getBookPublisher());
			System.out.println("상태: " + (book.getBookStatus() == 0 ? "대여가능"
					: book.getBookStatus() == 1 ? "대여중" : book.getBookStatus() == 2 ? "예약중" : "문의필요"));

			System.out.println("\n1: 대여하기 | 2: 예약하기 | 3: 북카트에 담기 | 4: 북카트 보기 | b: 이전 화면");
			String input = scanner.nextLine();
			String userId = SessionManager.getCurrentUserId();
			User user = new User();
			user.setUserId(userId);
			switch (input.toLowerCase()) {
			case "1":
				if (book.getBookStatus() == 0) {
					controller.rentOneBook(user, book);
					return book;
				} else if (book.getBookStatus() == 1) {
					System.out.println("대여중인 도서입니다.");
				} else if (book.getBookStatus() == 2) {
					List<BookReservation> reservationList = controller.getActiveReservationsByUserId(userId);
					boolean containsMyReservation = false;
					for (BookReservation reservation : reservationList) {
//						System.out.println(reservation.getBookUid());
//						System.out.println(book.getBookUid());
						if (reservation.getBookUid() == book.getBookUid())
							containsMyReservation = true;
					}
					if (containsMyReservation) {
						System.out.println("본인이 예약한 도서입니다.");
						controller.rentOneBook(user, book);
					} else
						System.out.println("예약중인 도서입니다.");
				} else
					System.out.println("기타 상태입니다. 관리자에게 문의하세요.");
				break;
			case "2":
				if (book.getBookStatus() == 1) {
					boolean success = controller.reserveBook(userId, book.getBookUid());
					System.out.println(success ? "도서 예약이 완료되었습니다." : "도서 예약에 실패했습니다.");
					return book;
				} else {
					System.out.println("대여 가능한 도서는 예약할 수 없습니다.");
				}
				break;

			case "3": {
				if (book.getBookStatus() != 0) {
					System.out.println("대여 중인 도서는 장바구니에 담을 수 없습니다.");
					break;
				}

				BookCartController cartController = new BookCartController();
				if (cartController.getCart().contains(book)) {
					System.out.println("이미 장바구니에 담긴 도서입니다.");
				} else {
					cartController.addToCart(book);
				}
				break;

			}
			case "4": {
				BookCartView cartView = new BookCartView();
				cartView.display(); // 북카트 보기
				break;
			}

			case "b":
				return book;

			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
}
