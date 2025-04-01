package presentation.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import business.dto.Book;
import exception.SearchWrongException;
import presentation.controller.BookController;
import util.ClearScreen;

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
			
			try {
				printMenu();
			System.out.print("메뉴 선택 > ");
			String inputString = null;
			inputString = sc.nextLine();
			switch (inputString) {
			case "1" -> {result.display(inputName());}
			case "2" -> {result.display(inputAuthor()); }
			case "3" -> {result.display(inputPublisher()); }
			case "9" -> isRunningMenu = false;
			default -> CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
			}
			}catch (SearchWrongException e) {
				System.out.println(e.getMessage());
			}
		} while (isRunningMenu);
		System.out.println("돌아갑니다."); // while문이 종료되므로 루프를 빠져나온다.
		System.out.println("엔터를 누르면 진행합니다 ...");
		new Scanner(System.in).nextLine();
	}
	
	List<Book> inputName() throws SearchWrongException {
		System.out.print("검색할 키워드");
		System.out.print("> ");
		String input = sc.nextLine();
		if (input.trim().isEmpty()) {
			throw new SearchWrongException("내용을 입력해 주세요");
		} else
			return controller.getBookByBookName(input);
	}

	List<Book> inputAuthor() throws SearchWrongException {
		System.out.println("저자 입력");
		System.out.print("> ");
		String input = sc.nextLine();
		if (input.trim().isEmpty()) {
			throw new SearchWrongException("내용을 입력해 주세요");
		} else
			return controller.getBookByBookAuthor(input);
	}

	List<Book> inputPublisher() throws SearchWrongException {
		System.out.println("출판사 입력");
		System.out.print("> ");
		String input = sc.nextLine();
		if (input.trim().isEmpty()) {
			throw new SearchWrongException("내용을 입력해 주세요");
		} else
			return controller.getBookByBookPublisher(input);
	}
	
	void printMenu() {
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
    	System.out.println("|                       검색 방식을 선택해주세요.                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
		System.out.println();
    	System.out.println("1. 제목으로 검색 | 2. 저자로 검색 | 3. 출판사로 검색 | 9. 돌아가기");
    	System.out.println();
    	System.out.println("--------------------------------------------------------------------------------");

	}
	
}