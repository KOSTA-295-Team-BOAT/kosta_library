package repository.dao;

import business.dto.RentDetail;

import java.sql.Connection;
import java.util.List;

/**
 * 대여상세와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface RentDetailDao {
    // 대여 상세 추가
    void addRentDetail(RentDetail rentDetail);

    // 대여 상세 ID로 조회
    RentDetail getRentDetailById(int rentDetailUid);

    // 모든 대여 상세 조회
    List<RentDetail> getAllRentDetails();

    // 대여 상세 업데이트
    void updateRentDetail(RentDetail rentDetail);

    // 대여 상세 삭제
    void deleteRentDetail(int rentDetailUid);

	void addRentDetail(Connection con, RentDetail rentDetail);
}
