package business.service.book;

import business.dto.Book;
import business.dto.BookReservation;
import business.dto.RentDetail;
import business.dto.User;
import exception.DmlException;
import repository.dao.BookDao;
import repository.dao.BookDaoImpl;
import repository.dao.BookReservationDao;
import repository.dao.BookReservationDaoImpl;
import repository.dao.RentDetailDao;
import repository.dao.RentDetailDaoImpl;
import repository.util.DbManager;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookReservationService {
    private final BookReservationDao reservationDao = new BookReservationDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final RentDetailDao rentDetailDao = new RentDetailDaoImpl();

    public static final int MAX_RENT_AND_RESERVE = 5;

    public boolean reserveBook(String userId, int bookUid) throws DmlException {
        Connection con = null;
        try {
            con = DbManager.getConnection();

            // 1. 도서 상태 확인
            Book book = bookDao.getBookById(bookUid);
            if (book == null) {
                System.out.println("존재하지 않는 도서입니다.");
                return false;
            }

            if (book.getBookStatus() == 0) {
                System.out.println("대여 가능한 도서는 예약할 수 없습니다.");
                return false;
            }

            // 2. 다른 사람이 예약했는지 확인
            BookReservation existingReservation = reservationDao.getActiveReservationByBookUid(con, bookUid);
            if (existingReservation != null) {
                if (!existingReservation.getUserId().equals(userId)) {
                    System.out.println("다른 사용자가 이미 예약한 도서입니다.");
                    return false;
                } else {
                    System.out.println("이미 이 도서를 예약한 상태입니다.");
                    return false;
                }
            }

            // 3. 대여+예약 권수 확인
            List<RentDetail> rentList = rentDetailDao.getRentDetailByUserId(con, userId); // 대여중 도서
            List<BookReservation> reserveList = reservationDao.getActiveReservationsByUserId(con, userId); // 예약중 도서
            int total = rentList.size() + reserveList.size();
            if (total >= MAX_RENT_AND_RESERVE) {
                System.out.println("대여 및 예약 가능한 최대 권수를 초과했습니다. (최대 " + MAX_RENT_AND_RESERVE + "권)");
                return false;
            }

            // 4. 예약 생성
            BookReservation reservation = new BookReservation();
            reservation.setBookUid(bookUid);
            reservation.setUserId(userId);
            reservation.setReservationStatus(0);

            LocalDateTime now = LocalDateTime.now();
            reservation.setReservationDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            reservation.setReservationDue(now.plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            reservationDao.addReservation(con, reservation);
            System.out.println("도서 예약이 완료되었습니다.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DmlException("예약 처리 중 오류가 발생했습니다.");
        } finally {
            DbManager.close(con, null, null);
        }
    }
    
    public List<BookReservation> getActiveReservationsByUserId(String userId) throws Exception {
        Connection con = null;
        try {
            con = DbManager.getConnection();
            return reservationDao.getActiveReservationsByUserId(con, userId);
        } finally {
            DbManager.close(con, null, null);
        }
    }
}