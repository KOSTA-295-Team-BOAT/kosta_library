package repository.dao;

import business.dto.BookRent;
import exception.DmlException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 도서 대여와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface BookRentDao {
    // 대여 추가
    void addBookRent(BookRent bookRent);

    // 대여 ID로 조회
	BookRent getBookRentById(Connection con, int rentUid) throws SQLException;

    // 모든 대여 조회
    List<BookRent> getAllBookRents();

    // 대여 업데이트
    void updateBookRent(BookRent bookRent);

    // 대여 삭제
    void deleteBookRent(String rentUid);

    //커넥션 받아서 추가
	BookRent addBookRent(Connection con, BookRent bookRent) throws SQLException, DmlException;

	void updateRentStatus(Connection con, int rentUid, int status) throws SQLException;

	void updateRentStatusAndReturnDate(Connection con, int rentUid, int status, String returnDate) throws SQLException;

}
