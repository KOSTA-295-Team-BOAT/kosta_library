package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import business.dto.User;
import repository.util.DbManager;

/**
 * 사용자 관련 DAO 구현 클래스
 * @author 황태윤
 */
public class UserDaoImpl implements UserDao {

	@Override
	public void addUser(User user) {
		// query 수정: user_name 컬럼 포함
		String sql = "INSERT INTO user (user_id, user_password, user_name, course_uid, user_status, user_score) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserPassword());
			ps.setString(3, user.getUserName());
			ps.setInt(4, user.getCourseUid());
			ps.setInt(5, user.getUserStatus());
			ps.setInt(6, user.getUserScore());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("addUser 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public User getUserById(String userId) {
		String sql = "SELECT * FROM user WHERE user_id = ?";
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					List<Integer> favoriteCategories = getUserFavoriteCategories(userId);
					return new User(rs.getString("user_id"), rs.getString("user_password"), rs.getInt("course_uid"),
							rs.getInt("user_status"), rs.getInt("user_score"), rs.getString("user_name"),
							favoriteCategories);
				}
			}
		} catch (SQLException e) {
			System.err.println("getUserById 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// 커넥션 받아서 처리하는 버전
	@Override
	public User getUserById(Connection con, String userId) throws SQLException {
	    String sql = "SELECT * FROM user WHERE user_id = ?";
	    try (PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                List<Integer> favoriteCategories = getUserFavoriteCategories(userId);
	                return new User(
	                    rs.getString("user_id"),
	                    rs.getString("user_password"),
	                    rs.getInt("course_uid"),
	                    rs.getInt("user_status"),
	                    rs.getInt("user_score"),
	                    rs.getString("user_name"),
	                    favoriteCategories
	                );
	            }
	        }
	    }
	    return null;
	}
	
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM user";
		try (Connection con = DbManager.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				List<Integer> favoriteCategories = getUserFavoriteCategories(rs.getString("user_id")); // 관심 카테고리 조회
				users.add(new User(rs.getString("user_id"), rs.getString("user_password"), rs.getInt("course_uid"),
						rs.getInt("user_status"), rs.getInt("user_score"), rs.getString("user_name"),
						favoriteCategories));
			}
		} catch (SQLException e) {
			System.err.println("getAllUsers 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void updateUser(User user) {
		String sql = "UPDATE user SET user_password = ?, user_name = ?, course_uid = ?, user_status = ?, user_score = ? WHERE user_id = ?";
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, user.getUserPassword());
			ps.setString(2, user.getUserName());
			ps.setInt(3, user.getCourseUid());
			ps.setInt(4, user.getUserStatus());
			ps.setInt(5, user.getUserScore());
			ps.setString(6, user.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("updateUser 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean isValidUser(String userId, String password) {
		String sql = "SELECT user_id, user_password FROM user WHERE user_id = ? AND user_password = ?";
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.err.println("isValidUser 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public List<Integer> getUserFavoriteCategories(String userId) {
		String sql = "SELECT category_uid FROM user_favorite WHERE user_id = ?";
		List<Integer> favoriteCategories = new ArrayList<>();
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					favoriteCategories.add(rs.getInt("category_uid"));
				}
			}
		} catch (SQLException e) {
			System.err.println("getUserFavoriteCategories 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return favoriteCategories;
	}

	public Set<Integer> getUserCategoryIds(String userId) {
        Set<Integer> ids = new HashSet<>();
        String sql = "SELECT category_uid FROM user_favorite WHERE user_id = ?";
        
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("category_uid"));
            }
        } catch (SQLException e) {
            System.err.println("getUserCategoryIds 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return ids;
    }

	public void addUserFavoriteCategory(String userId, int categoryUid) {
		String sql = "INSERT INTO user_favorite (user_id, category_uid) VALUES (?, ?)";
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setInt(2, categoryUid);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("addUserFavoriteCategory 실행 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUserFavoriteCategory(String userId, int categoryUid) {
		String sql = "DELETE FROM user_favorite WHERE user_id = ? AND category_uid = ?";
		try (Connection conn = DbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setInt(2, categoryUid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserScore(Connection con, String userId, int newScore) throws SQLException {
		String sql = "UPDATE user SET user_score = ? WHERE user_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, newScore);
			ps.setString(2, userId);
			ps.executeUpdate();
		}
	}
}