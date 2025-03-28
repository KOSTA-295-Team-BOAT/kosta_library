package repository.dao;

import business.dto.BookReservation;
import java.util.List;

/**
 * 도서 예약과 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface BookReservationDao {
    // 예약 추가
    void addReservation(BookReservation reservation);

    // 예약 ID로 조회
    BookReservation getReservationById(int reservationUid);

    // 모든 예약 조회
    List<BookReservation> getAllReservations();

    // 예약 업데이트
    void updateReservation(BookReservation reservation);

    // 예약 삭제
    void deleteReservation(int reservationUid);
}
