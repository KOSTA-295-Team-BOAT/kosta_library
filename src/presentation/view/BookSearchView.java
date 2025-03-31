package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.Book;
import presentation.controller.BookController;

/**
 * 도서 검색 뷰 클래스
 * @author 박재현
 * @since 2025-03-29
 */
public class BookSearchView {

	Scanner sc = new Scanner(System.in);
	BookController controller;
	BookSearchResultView result;

	public BookSearchView(BookController controller) {
		// 생성자 주입으로 컨트롤러를 초기화한 경우
		this.controller = controller;
		this.result = new BookSearchResultView(controller);
	}

	public void display() {
		System.out.println("도서 검색 화면");

		boolean isRunningMenu = true;

		do {
			System.out.println("1. 제목으로 검색 | 2. 저자로 검색 | 3. 출판사로 검색 | 9. 돌아가기");
			System.out.print(">");
			String inputString = null;
			inputString = sc.nextLine();
			sc.nextLine();
			switch (inputString) {
			case "1" -> {result.display(inputName());}
			case "2" -> {result.display(inputAuthor()); }
			case "3" -> {result.display(inputPublisher()); }
			case "9" -> isRunningMenu = false;
			default -> System.out.println("잘못된 입력입니다.");
			}
		} while (isRunningMenu);
		System.out.println("돌아갑니다."); // while문이 종료되므로 루프를 빠져나온다.
	}
	
	List<Book> inputName() {
		System.out.println("검색할 책 입력"); System.out.print(">");
		String input = sc.nextLine();
		return controller.getBookByBookName(input);
	}

	List<Book> inputAuthor() {
		System.out.println("저자 입력"); System.out.print(">");
		String input = sc.nextLine();
		sc.nextLine();
		return controller.getBookByBookAuthor(input);
	}

	List<Book> inputPublisher() {
		System.out.println("출판사 입력"); System.out.print(">");
		String input = sc.nextLine();
		return controller.getBookByBookPublisher(input);
	}
}