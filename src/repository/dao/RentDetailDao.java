package repository.dao;

import business.dto.BookRent;
import business.dto.RentDetail;
import repository.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 대여상세와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface RentDetailDao {
    // 대여 상세 추가
	public RentDetail addRentDetail(Connection con, RentDetail rentDetail) throws SQLException;
	
	//특정 유저의 대여 상세 조회
	List<RentDetail> getRentDetailByUserId(Connection con, String userId) throws SQLException;

	List<RentDetail> getNotReturnedDetailByRentUid(Connection con, int rentUid) throws SQLException;

	void updateRentDetail(Connection con, RentDetail rentDetail) throws SQLException;

	RentDetail getRentDetailById(Connection con, int rentDetailUid) throws SQLException;

}
