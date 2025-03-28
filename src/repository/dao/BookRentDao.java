package repository.dao;

import business.dto.BookRent;
import java.util.List;

/**
 * 도서 대여와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface BookRentDao {
    // 대여 추가
    void addBookRent(BookRent bookRent);

    // 대여 ID로 조회
    BookRent getBookRentById(String rentUid);

    // 모든 대여 조회
    List<BookRent> getAllBookRents();

    // 대여 업데이트
    void updateBookRent(BookRent bookRent);

    // 대여 삭제
    void deleteBookRent(String rentUid);
}
