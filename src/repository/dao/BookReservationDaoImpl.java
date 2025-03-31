package repository.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import business.dto.BookReservation;
import repository.util.DbManager;

public class BookReservationDaoImpl implements BookReservationDao {

    @Override
    public void addReservation(BookReservation reservation) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DbManager.getConnection();
            String sql = "INSERT INTO book_reservation (book_uid, reservation_status, reservation_date, reservation_due, user_id) VALUES (?, 0, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, reservation.getBookUid());
            ps.setString(2, reservation.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, null);
        }
    }

    @Override
    public BookReservation getReservationById(int reservationUid) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookReservation reservation = null;
        
        try {
            con = DbManager.getConnection();
            String sql = "SELECT * FROM book_reservation WHERE reservation_uid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, reservationUid);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                reservation = createReservationFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }
        return reservation;
    }

    @Override
    public List<BookReservation> getAllReservations() {
        List<BookReservation> reservations = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DbManager.getConnection();
            String sql = "SELECT * FROM book_reservation";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                reservations.add(createReservationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }
        return reservations;
    }

    @Override
    public void updateReservation(BookReservation reservation) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DbManager.getConnection();
            String sql = "UPDATE book_reservation SET reservation_status = ? WHERE reservation_uid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, reservation.getReservationStatus());
            ps.setInt(2, reservation.getReservationUid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, null);
        }
    }

    @Override
    public void deleteReservation(int reservationUid) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DbManager.getConnection();
            String sql = "DELETE FROM book_reservation WHERE reservation_uid = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, reservationUid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, null);
        }
    }

    private BookReservation createReservationFromResultSet(ResultSet rs) throws SQLException {
        BookReservation reservation = new BookReservation();
        reservation.setReservationUid(rs.getInt("reservation_uid"));
        reservation.setBookUid(rs.getInt("book_uid"));
        reservation.setReservationStatus(rs.getInt("reservation_status"));
        reservation.setReservationDate(rs.getString("reservation_date"));
        reservation.setReservationDue(rs.getString("reservation_due"));
        reservation.setUserId(rs.getString("user_id"));
        return reservation;
    }
}
