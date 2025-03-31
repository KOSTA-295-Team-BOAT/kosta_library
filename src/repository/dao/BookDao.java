package repository.dao;

import business.dto.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 도서와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface BookDao {
    // 도서 추가
    void addBook(Book book);

    // 도서 ID로 조회
    Book getBookById(int bookUid) throws SQLException;

    // 모든 도서 조회
    List<Book> getAllBooks() throws SQLException;

    // 도서 업데이트
    void updateBook(Book book)throws SQLException;

    // 도서 삭제
    void deleteBook(int bookUid) throws SQLException;
    
    //도서명으로 검색
    public List<Book> getBookByBookName(String name) throws SQLException;

    //출판사로 검색
	public List<Book> getBookByBookPublisher(String name) throws SQLException;

	//저자로 검색
	public List<Book> getBookByAuthor(String name) throws SQLException;

	//도서 대여 상태 변경
	void updateBookStatus(Connection con, Book book, int bookStatus) throws SQLException;

}
