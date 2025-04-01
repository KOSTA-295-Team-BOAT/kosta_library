package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.Book;
import business.dto.User;
import presentation.controller.BookController;
import util.SessionManager;

public class BookSearchResultView {
	final int DISPLAY_BOOK_PER_PAGE = 5;
	final int MAX_BOOK_NAME_LENGTH = 45;
	Scanner scanner = new Scanner(System.in);
	BookController controller;
	
	BookSearchResultView(BookController controller) {
		// 생성자 주입으로 컨트롤러 초기화
		this.controller = controller;
	}
	
	
	List<Book> book =null;
	void display(List<Book> book) {
		if (book==null)
			return;
		this.book=book;
		System.out.println("도서 검색 결과");
//		System.out.println(book);
		boolean isRunning = true;
		int maxPage = 0;
		maxPage = (book.size() + DISPLAY_BOOK_PER_PAGE - 1) / DISPLAY_BOOK_PER_PAGE;
		int currentPage = 1;
		while (isRunning) {
			String bookName = null;
			int bookStartIndex = (currentPage - 1) * DISPLAY_BOOK_PER_PAGE;
			int currentLength = Math.min(DISPLAY_BOOK_PER_PAGE, book.size() - bookStartIndex);
			System.out.println("--------------------------------------------------------------------------------");			
			for (int i = 0; i < currentLength; i++) {
				if (book.get(bookStartIndex+i).getBookName().length() > MAX_BOOK_NAME_LENGTH)
					bookName = book.get(bookStartIndex+i).getBookName().substring(0, MAX_BOOK_NAME_LENGTH);
				else
					bookName = book.get(bookStartIndex+i).getBookName();
				System.out.println("[" + (i+1) + "]" + " 제목 : " + bookName + " | 저자 : " + book.get(bookStartIndex+i).getBookAuthor()
						+ " | 출판사 : " + book.get(bookStartIndex+i).getBookPublisher() + " ");
			}

			System.out.println(currentPage + "/" + maxPage);
			System.out.println("p : 이전 페이지 | n : 다음 페이지 | c : 북카트에 담기 | 1-" + currentLength  + " : 도서 대여 | b : 돌아가기");
			String str = scanner.nextLine();
			try {
				switch (str) {
				case "p" -> currentPage=prevPage(currentPage);
				case "n" -> currentPage=nextPage(currentPage,maxPage);
				case "c" -> System.out.println("북카트에 담기 기능(구현예정)");
				
				case "1" -> book.set(bookStartIndex + 0, displayBookDetail(book.get((bookStartIndex + 0))));
				case "2" -> book.set(bookStartIndex + 1, displayBookDetail(book.get((bookStartIndex + 1))));
				case "3" -> book.set(bookStartIndex + 2, displayBookDetail(book.get((bookStartIndex + 2))));
				case "4" -> book.set(bookStartIndex + 3, displayBookDetail(book.get((bookStartIndex + 3))));
				case "5" -> book.set(bookStartIndex + 4, displayBookDetail(book.get((bookStartIndex + 4))));
				
//				case "1" -> book.set(bookStartIndex + 0, callRent(book.get((bookStartIndex + 0))));
//				case "2" -> book.set(bookStartIndex + 1, callRent(book.get((bookStartIndex + 1))));
//				case "3" -> book.set(bookStartIndex + 2, callRent(book.get((bookStartIndex + 2))));
//				case "4" -> book.set(bookStartIndex + 3, callRent(book.get((bookStartIndex + 3))));
//				case "5" -> book.set(bookStartIndex + 4, callRent(book.get((bookStartIndex + 4))));
				case "b" -> isRunning=false;
				default -> System.out.println("잘못된 입력입니다.");
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

	int prevPage(int currentPage) {
		if (currentPage==1) return currentPage;
		else return currentPage-1;
		
	}
	int nextPage(int currentPage, int maxPage) {
		if (currentPage == maxPage)	return currentPage;
		else return currentPage+1;
		
	}
	
	void printBookListPage(int currentPage, List<Book> book) {
		for (int i = 0; i < DISPLAY_BOOK_PER_PAGE; i++)
			System.out.println(book.get((((currentPage - 1) * DISPLAY_BOOK_PER_PAGE) + i)));
	}

	Book displayBookDetail(Book book) {
		BookDetailView view = new BookDetailView();
		book = view.display(book);
		return book;
		
	}
	
	Book callRent(Book book) {
		try {
			controller.rentOneBook(makeIdOnlyDto(SessionManager.getCurrentUserId()), book);
			book.setBookStatus(1);
		} catch (Exception e) {
			System.out.println("잘못된 입력입니다.");
		}
		return book;
	}

}
