package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.dto.BookRent;
import business.dto.RentDetail;
import repository.util.DbManager;

public class RentDetailDaoImpl implements RentDetailDao {

	@Override
	public RentDetail addRentDetail(Connection con, RentDetail rentDetail) throws SQLException {
		PreparedStatement ps = null;

		try {
			// connection은 이미 받아왔으므로 새로 만들지 않음
			String query = "insert into rent_detail (book_uid, rent_uid, rent_return_state, rent_return_due) values(?,?,?,?)";
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// RETURN_GENERATED_KEYS : 최근 실행된 쿼리의 auto-increment 값을 받아옴

			ps.setInt(1, rentDetail.getBookUid());
			ps.setInt(2, rentDetail.getRentUid());
			ps.setInt(3, 0);
			ps.setString(4, rentDetail.getRentReturnDue());

			int result = ps.executeUpdate();
			if (result == 0) {
				throw new SQLException("삽입실패");
			}
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					rentDetail.setRentDetailUid(rs.getInt(1));
			}

		} finally {
			DbManager.close(null, ps, null); // ps, rs는 닫지만 con은 닫지않음. rs는 auto-close
		}
		return rentDetail;
	}

	@Override
	public List<RentDetail> getRentDetailByUserId(Connection con, String userId) throws SQLException {
		List<RentDetail> rentDetailList = new ArrayList<RentDetail>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		RentDetail rentDetail = null;

		try {
			// connection은 이미 받아왔으므로 새로 만들지 않음
			String query = "SELECT rd.* FROM rent_detail rd JOIN book_rent br ON rd.rent_uid = br.rent_uid WHERE br.user_id = ?  AND rd.rent_return_state = 0";
			ps = con.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				rentDetail = new RentDetail(rs.getInt("rent_detail_uid"), rs.getInt("book_uid"), rs.getInt("rent_uid"),
						rs.getInt("rent_return_state"), rs.getString("rent_return_due"));
				rentDetailList.add(rentDetail);
			}

		} finally {
			DbManager.close(null, ps, null); // ps, rs는 닫지만 con은 닫지않음. rs는 auto-close
		}
		return rentDetailList;
	}

	@Override
	public RentDetail getRentDetailById(Connection con, int rentDetailUid) throws SQLException {
		String query = "SELECT * FROM rent_detail WHERE rent_detail_uid = ?";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, rentDetailUid);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new RentDetail(rs.getInt("rent_detail_uid"), rs.getInt("book_uid"), rs.getInt("rent_uid"),
							rs.getInt("rent_return_state"), rs.getString("rent_return_due"));
				}
			}
		}
		return null;
	}

	@Override
	public void updateRentDetail(Connection con, RentDetail rentDetail) throws SQLException {
		String query = "UPDATE rent_detail SET rent_return_state = ? WHERE rent_detail_uid = ?";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, rentDetail.getRentReturnState());
			ps.setInt(2, rentDetail.getRentDetailUid());
			ps.executeUpdate();
		}
	}

	@Override
	public List<RentDetail> getNotReturnedDetailByRentUid(Connection con, int rentUid) throws SQLException {
		List<RentDetail> list = new ArrayList<>();
		String query = "SELECT * FROM rent_detail WHERE rent_uid = ? AND rent_return_state = 0";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, rentUid);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					RentDetail detail = new RentDetail(rs.getInt("rent_detail_uid"), rs.getInt("book_uid"),
							rs.getInt("rent_uid"), rs.getInt("rent_return_state"), rs.getString("rent_return_due"));
					list.add(detail);
				}
			}
		}
		return list;
	}
}
