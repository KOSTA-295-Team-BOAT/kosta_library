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

	@Override
	public List<BookReservation> getActiveReservationsByUserId(Connection con, String userId) throws SQLException {
		List<BookReservation> list = new ArrayList<>();
		String sql = "SELECT * FROM book_reservation WHERE user_id = ? AND reservation_status = 0";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(createReservationFromResultSet(rs));
				}
			}
		}
		return list;
	}

	@Override
	public boolean userAlreadyReserved(Connection con, String userId, int bookUid) throws SQLException {
		String sql = "SELECT COUNT(*) FROM book_reservation WHERE book_uid = ? AND user_id = ? AND reservation_status = 0";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, bookUid);
			ps.setString(2, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	@Override
	public BookReservation getActiveReservationByBookUid(Connection con, int bookUid) throws SQLException {
		String sql = "SELECT * FROM book_reservation WHERE book_uid = ? AND reservation_status = 0";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, bookUid);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return createReservationFromResultSet(rs);
				}
			}
		}
		return null;
	}

	@Override
	//비즈니스 로직에서 커넥션 받는 버전 (트랜잭션 대응)
	public void addReservation(Connection con, BookReservation reservation) {
		String sql = "INSERT INTO book_reservation (book_uid, reservation_status, reservation_date, reservation_due, user_id) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, reservation.getBookUid());
			ps.setInt(2, reservation.getReservationStatus());
			ps.setString(3, reservation.getReservationDate());
			ps.setString(4, reservation.getReservationDue());
			ps.setString(5, reservation.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
