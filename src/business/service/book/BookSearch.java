package business.service.book;

import java.sql.SQLException;
import java.util.List;

import business.dto.Book;
import exception.SearchWrongException;
import repository.dao.BookCategoryDao;
import repository.dao.BookCategoryDaoImpl;
import repository.dao.BookDao;
import repository.dao.BookDaoImpl;
import repository.dao.BookRentDao;
import repository.dao.BookRentDaoImpl;
import repository.dao.BookReservationDao;
import repository.dao.BookReservationDaoImpl;

/**
 * 도서 검색과 관련된 비즈니스 로직
 */
public class BookSearch {

	BookCategoryDao bookCategoryDao;
	BookDao bookDao;
	BookRentDao bookRentDao;
	BookReservationDao bookReservationDao;
	
	public BookSearch(){
		bookSearchInit();
	}
	
	void bookSearchInit(){
		bookCategoryDao = new BookCategoryDaoImpl();
		bookDao = new BookDaoImpl();
//		bookRentDao = new BookRentDaoImpl();
		bookReservationDao = new BookReservationDaoImpl();

	}
	
	public List<Book> searchBookByName(String name) throws SearchWrongException {
		
		List<Book> returnBookList;
 		
		try {
			returnBookList = bookDao.getBookByBookName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		return returnBookList;
		
	}

	public List<Book> searchBookByAuthor(String name) throws SearchWrongException {
		
		List<Book> returnBookList;
 		
		try {
			returnBookList = bookDao.getBookByAuthor(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		return returnBookList;
		
	}
	public List<Book> searchBookByPublisher(String name) throws SearchWrongException {
		
		List<Book> returnBookList;
 		
		try {
			returnBookList = bookDao.getBookByBookPublisher(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		return returnBookList;
		
	}

	
	
	
}
