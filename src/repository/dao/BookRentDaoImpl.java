package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.dto.Book;
import business.dto.BookRent;
import exception.DmlException;
import repository.util.DbManager;

public class BookRentDaoImpl implements BookRentDao {

	@Override
	public BookRent addBookRent(Connection con, BookRent bookRent) throws SQLException {
		// 도서 대여 정보 추가
		PreparedStatement ps = null;

		try {
			// connection은 이미 받아왔으므로 새로 만들지 않음

			String query = "insert into book_rent (user_id, rent_date, rent_status, rent_due) values(?,?,?,?)";
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// RETURN_GENERATED_KEYS : 최근 실행된 쿼리의 auto-increment 값을 받아옴

			ps.setString(1, bookRent.getUserId());
			ps.setString(2, bookRent.getRentDate());
			ps.setInt(3, bookRent.getRentStatus());
			ps.setString(4, bookRent.getRentDue());

			int result = ps.executeUpdate();
			if (result == 0) {
				throw new SQLException("삽입실패");
			}
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					bookRent.setRentUid(rs.getInt(1));
			}

		} finally {
			DbManager.close(null, ps, null); // ps, rs는 닫지만 con은 닫지않음. rs는 auto-close
		}
		return bookRent;
	}

	@Override
	public void updateRentStatus(Connection con, int rentUid, int status) throws SQLException {
	    String sql = "UPDATE book_rent SET rent_status = ? WHERE rent_uid = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, status);
	        ps.setInt(2, rentUid);
	        ps.executeUpdate();
	    }
	}
	
	@Override
	public void updateRentStatusAndReturnDate(Connection con, int rentUid, int status, String returnDate) throws SQLException {
	    String sql = "UPDATE book_rent SET rent_status = ?, rent_return_date = ? WHERE rent_uid = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, status);
	        ps.setString(2, returnDate);
	        ps.setInt(3, rentUid);
	        ps.executeUpdate();
	    }
	}
	
	
	@Override
	public BookRent getBookRentById(Connection con, int rentUid) throws SQLException {
	    String query = "SELECT * FROM book_rent WHERE rent_uid = ?";
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, rentUid);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                BookRent rent = new BookRent();
	                rent.setRentUid(rs.getInt("rent_uid"));
	                rent.setUserId(rs.getString("user_id"));
	                rent.setRentStatus(rs.getInt("rent_status"));
	                rent.setRentDate(rs.getString("rent_date"));
	                rent.setRentDue(rs.getString("rent_due"));
	                return rent;
	            }
	        }
	    }
	    return null;
	}

	@Override
	public List<BookRent> getAllBookRents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBookRent(BookRent bookRent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBookRent(String rentUid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addBookRent(BookRent bookRent) {
		// TODO Auto-generated method stub

	}

}
