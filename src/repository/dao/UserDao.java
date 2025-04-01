package repository.dao;

import business.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 이용자 정보와 관련된 DAO 기능을 정의하는 인터페이스
 * @author  황태윤
 * @since 2025.3.27
 */
public interface UserDao {
    
    // 회원 추가 (user_name 포함)
    void addUser(User user);

    // 회원 ID로 조회 (user_name 포함)
    User getUserById(String userId);

    // 모든 회원 조회 (user_name 포함)
    List<User> getAllUsers();

    // 회원 정보 업데이트 (user_name 포함)
    void updateUser(User user);

    // 로그인 검증 메서드
    boolean isValidUser(String userId, String password);

    // 회원 즐겨찾기 카테고리 추가
    void addUserFavoriteCategory(String userId, int categoryUid);

    // 회원 즐겨찾기 카테고리 삭제
    void deleteUserFavoriteCategory(String userId, int categoryUid);

    // 회원 즐겨찾기 카테고리 조회
    List<Integer> getUserFavoriteCategories(String userId);

	void updateUserScore(Connection con, String userId, int updatedScore) throws SQLException;

	User getUserById(Connection con, String userId) throws SQLException;
}
