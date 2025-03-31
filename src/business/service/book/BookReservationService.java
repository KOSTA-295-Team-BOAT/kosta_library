package business.service.book;

import business.dto.BookReservation;
import repository.dao.BookReservationDao;
import repository.dao.BookReservationDaoImpl;

public class BookReservationService {
    private final BookReservationDao reservationDao = new BookReservationDaoImpl();

    public boolean reserveBook(String userId, int bookUid) {
        try {
            BookReservation reservation = new BookReservation();
            reservation.setUserId(userId);
            reservation.setBookUid(bookUid);
            
            // 예약 정보 추가
            reservationDao.addReservation(reservation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
