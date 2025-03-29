package presentation.view;

import java.util.Scanner;
import presentation.controller.BookController;

public class BookSearchView {

	Scanner sc = new Scanner(System.in);
	BookController controller;
	BookSearchResultView result;


	BookSearchView(BookController controller) {
		// 생성자 주입으로 컨트롤러를 초기화한 경우
		this.controller = controller;
		this.result = new BookSearchResultView();
	}

	void display() {
		System.out.println("도서 검색 화면");
		System.out.println("1. 제목으로 검색 | 2. 저자로 검색 | 3. 출판사로 검색 | 9. 돌아가기");

		boolean isRunningMenu = true;

		while (isRunningMenu) {

			String inputString = null;
			System.out.println("검색할 책 입력");
			System.out.print(">");
			inputString = sc.nextLine();
			switch (inputString) {
			case "1" -> result.display(controller.getBookByBookName(sc.nextLine()));
			case "2" -> result.display(controller.getBookByBookAuthor(sc.nextLine()));
			case "3" -> result.display(controller.getBookByBookPublisher(sc.nextLine()));
			case "9" -> isRunningMenu = false;
			default -> System.out.println("잘못된 입력입니다.");
			}
		}System.out.println("돌아갑니다.");
	}

}