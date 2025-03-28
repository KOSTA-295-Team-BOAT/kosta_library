package repository.dao;

import business.dto.Book;
import java.util.List;

/**
 * 도서와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface BookDao {
    // 도서 추가
    void addBook(Book book);

    // 도서 ID로 조회
    Book getBookById(int bookUid);

    // 모든 도서 조회
    List<Book> getAllBooks();

    // 도서 업데이트
    void updateBook(Book book);

    // 도서 삭제
    void deleteBook(int bookUid);
}
