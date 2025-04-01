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
 * 
 * @author 박재현
 * @since 2025-03-29
 */
public class BookSearchService {

	BookCategoryDao bookCategoryDao;
	BookDao bookDao;
	BookRentDao bookRentDao;
	BookReservationDao bookReservationDao;

	public BookSearchService() {
		bookSearchInit();
	}

	void bookSearchInit() {
		bookCategoryDao = new BookCategoryDaoImpl();
		bookDao = new BookDaoImpl();
		bookRentDao = new BookRentDaoImpl();
		bookReservationDao = new BookReservationDaoImpl();

	}

	public List<Book> searchBookByName(String name) throws SearchWrongException {

		List<Book> returnBookList;
		try {
			returnBookList = bookDao.getBookByBookName(name);
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		if (returnBookList.isEmpty())
			throw new SearchWrongException("검색 결과가 없습니다.");
		else
			return returnBookList;

	}

	public List<Book> searchBookByAuthor(String name) throws SearchWrongException {

		List<Book> returnBookList;

		try {
			returnBookList = bookDao.getBookByAuthor(name);
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		if (returnBookList.isEmpty())
			throw new SearchWrongException("검색 결과가 없습니다.");
		else
			return returnBookList;

	}

	public List<Book> searchBookByPublisher(String name) throws SearchWrongException {

		List<Book> returnBookList;

		try {
			returnBookList = bookDao.getBookByBookPublisher(name);
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		if (returnBookList.isEmpty())
			throw new SearchWrongException("검색 결과가 없습니다.");
		else
			return returnBookList;

	}

	public Book searchBookById(int bookUid) throws SearchWrongException {
		Book book = null;

		try {
			book = bookDao.getBookById(bookUid);
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new SearchWrongException(e.getMessage());
		}
		if (book == null)
			throw new SearchWrongException("검색 결과가 없습니다");
		else
			return book;

	}

	// 합의한 적 없는 기능을 멋대로 GPT가 추가하려고 함... 나중에 만들수도 있으니 일단 주석처리
//	// 신규 추가: 관심분야 코드에 따라 도서를 검색하며, 없는 경우 예외 발생
//	public List<Book> searchBookByInterest(String interestCode) throws SearchWrongException {
//		// 가정: bookCategoryDao.getCategoryByCode()가 null을 리턴하면 존재하지 않는 관심분야임
//		if(bookCategoryDao.getCategoryByCode(interestCode) == null) {
//			throw new SearchWrongException("없는 관심분야 입니다.");
//		}
//		try {
//			List<Book> returnBookList = bookDao.getBookByCategoryCode(interestCode); // 가정: 해당 메서드 존재
//			return returnBookList;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new SearchWrongException(e.getMessage());
//		}
//	}
}
