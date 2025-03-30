package presentation.controller;

import java.sql.SQLException;
import java.util.List;



import business.dto.Book;
import business.service.book.BookSearch;
import exception.SearchWrongException;

/** 
 * 기능별 상세 컨트롤러 : 도서 컨트롤러 클래스
 * @author 박재현
 * @since 2025-03-29
 */
public class BookController {
	
	public BookController(){
		
	}
	
	public List<Book> getBookByBookName(String name) {					
		BookSearch search = new BookSearch();
		List<Book> returnBookList = null;
		try {
			returnBookList = search.searchBookByName(name);
		} catch (SearchWrongException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("예외발생");
			//TODO 뷰 연결
		}
		return returnBookList;
	}

	public List<Book> getBookByBookAuthor(String name) {					
		BookSearch search = new BookSearch();
		List<Book> returnBookList = null;
		try {
			returnBookList = search.searchBookByAuthor(name);
		} catch (SearchWrongException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("예외발생");
			//TODO 뷰 연결
		}
		return returnBookList;
	}

	public List<Book> getBookByBookPublisher(String name) {					
		BookSearch search = new BookSearch();
		List<Book> returnBookList = null;
		try {
			returnBookList = search.searchBookByPublisher(name);
		} catch (SearchWrongException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("예외발생");
			//TODO 뷰 연결
		}
		return returnBookList;
	}

	
	
	
}
