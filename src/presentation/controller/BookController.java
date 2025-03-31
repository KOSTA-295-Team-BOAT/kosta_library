package presentation.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.dto.Book;
import business.dto.BookRent;
import business.dto.RentDetail;
import business.dto.User;
import business.service.book.BookRentService;
import business.service.book.BookSearchService;
import exception.DmlException;
import exception.SearchWrongException;

/**
 * 기능별 상세 컨트롤러 : 도서 컨트롤러 클래스
 * 
 * @author 박재현
 * @since 2025-03-29
 */
public class BookController {

	public BookController() {

	}

	public List<Book> getBookByBookName(String name) {
		BookSearchService search = new BookSearchService();
		List<Book> returnBookList = null;
		try {
			returnBookList = search.searchBookByName(name);
		} catch (SearchWrongException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
//			System.out.println("제목으로 검색중 예외발생");
			System.out.println(e.getMessage());
			// TODO 뷰 연결
		}
		return returnBookList;
	}

	public List<Book> getBookByBookAuthor(String name) {
		BookSearchService search = new BookSearchService();
		List<Book> returnBookList = null;
		try {
			returnBookList = search.searchBookByAuthor(name);
		} catch (SearchWrongException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
//			System.out.println("저자 검색중 예외발생");
			System.out.println(e.getMessage());
			// TODO 뷰 연결
		}
		return returnBookList;
	}

	public List<Book> getBookByBookPublisher(String name) {
		BookSearchService search = new BookSearchService();
		List<Book> returnBookList = null;
		try {
			returnBookList = search.searchBookByPublisher(name);
		} catch (SearchWrongException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
//			System.out.println("출판사 검색중 예외발생");
			System.out.println(e.getMessage());
			// TODO 뷰 연결
		}
		return returnBookList;
	}

	public BookRent rentOneBook(User user, Book book) {
		BookRentService service = new BookRentService();
		BookRent rent = null;
		try {
			rent = service.rentOneBook(user, book);
			System.out.println(book.getBookName() + "도서 대여에 성공했습니다."); // 나중에 출력용 뷰로 넘겨서 찍도록 리팩토링
		} catch (DmlException e) {
			System.out.println(e.getMessage());
			// TODO 뷰 연결
		} catch (Exception ae){
			System.out.println("대여 중 예외 발생");
		}
		return rent;
	}

	public BookRent rentBooks(User user, List<Book> books) {
		BookRentService service = new BookRentService();
		BookRent rent = null;
		try {
			rent = service.rentBooks(user, books);
		} catch (Exception e) {
			System.out.println("도서 여러권 대여중 예외발생");
			// TODO 뷰 연결
		}
		return rent;
		
				
	}

	public List<RentDetail> getRentDetailByUserId(String userId) {
		BookRentService service = new BookRentService();
		List<RentDetail> rentDetailList = new ArrayList<RentDetail>();
		try {
			rentDetailList = service.getRentDetailByUserId(userId);
		}catch (Exception e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
//			System.out.println("대여상세 조회 중 예외발생");
		}
		return rentDetailList;
		
	}
	
	public Book getBookById(int BookUid) {
		BookSearchService service = new BookSearchService();
		Book book = new Book();
		try{
			book = service.searchBookById(BookUid);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return book;
		
	}

}
