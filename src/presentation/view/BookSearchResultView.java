package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.Book;
import business.dto.User;
import presentation.controller.BookController;
import util.SessionManager;

public class BookSearchResultView {
	final int DISPLAY_BOOK_PER_PAGE = 5;
	final int MAX_BOOK_NAME_LENGTH = 15;
	Scanner scanner = new Scanner(System.in);
	BookController controller;

	BookSearchResultView(BookController controller) {
		// 생성자 주입으로 컨트롤러 초기화
		this.controller = controller;
	}

	void display(List<Book> book) {
		System.out.println("도서 검색 결과");
//		System.out.println(book);
		boolean isRunning = true;
		int page = 0;
		page = (book.size() / DISPLAY_BOOK_PER_PAGE) + 1;
		int currentPage = 1;
		while (isRunning) {
			String bookName = null;
			// System.out.println(book);
			System.out.println("-----------------------------------------------------------------------------");
			int currentLength = book.size() % DISPLAY_BOOK_PER_PAGE;
			for (int i = 0; i < currentLength; i++) {
				if (book.get(i).getBookName().length() > MAX_BOOK_NAME_LENGTH)
					bookName = book.get(i).getBookName().substring(0, MAX_BOOK_NAME_LENGTH);
				else
					bookName = book.get(i).getBookName();
				System.out.println("[" + i + "]" + " 제목 : " + bookName + " | 저자 : " + book.get(i).getBookAuthor()
						+ " | 출판사 : " + book.get(i).getBookPublisher() + " ");
//				System.out.println(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + i)));
			}

			System.out.println(currentPage + "/" + page);
			System.out.println("p : 이전 페이지 | n : 다음 페이지 | c : 북카트에 담기 | 번호 : 도서 대여 | b : 돌아가기");
			String str = scanner.nextLine();
			try {
				switch (str) {
				case "p" -> System.out.println("이전페이지기능(구현예정)");
				case "n" -> System.out.println("다음페이지기능(구현예정)");
				case "c" -> System.out.println("북카트에 담기 기능(구현예정)");
				case "1" -> callRent(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + 0)));
				case "2" -> callRent(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + 1)));
				case "3" -> callRent(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + 2)));
				case "4" -> callRent(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + 3)));
				case "5" -> callRent(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + 4)));
				case "b" -> System.out.println("돌아가기");
				}
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	User makeIdOnlyDto(String userId) { // 세션ID만 있고 유저정보 전체를 들고있지 않은데 Rent에서는 UserDto 전체를 쓰고있다... 리팩토링할 시간 없으니 그냥 어댑터
										// 만들어서 대응... 어차피 Rent에선 UserId밖에 안쓴다
		User returnDto = new User();
		returnDto.setUserId(userId);
		return returnDto;

	}

	void printBookListPage(int currentPage, List<Book> book) {
		for (int i = 0; i < DISPLAY_BOOK_PER_PAGE; i++)
			System.out.println(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + i)));
	}

	void callRent(Book book) {
		try {
			controller.rentOneBook(makeIdOnlyDto(SessionManager.getCurrentUserId()), book);
		} catch (Exception e) {
			System.out.println("잘못된 입력입니다.");
		}
	}

}
