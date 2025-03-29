package presentation.model;

import business.dto.User;
import repository.dao.UserDao;
import repository.util.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 관련 DAO 구현 클래스
 */
public class UserDaoImpl implements UserDao {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user (user_id, user_password, course_uid, category_uid, user_status, user_score) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("DB 연결 성공: addUser 메서드 실행 중"); // 디버깅 메시지
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserPassword());
            ps.setInt(3, user.getCourseUid());
            ps.setObject(4, user.getCategoryUid() == 0 ? null : user.getCategoryUid());
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
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("DB 연결 성공: getUserById 메서드 실행 중"); // 디버깅 메시지
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("user_password"),
                            rs.getInt("course_uid"),
                            rs.getInt("category_uid"),
                            rs.getInt("user_status"),
                            rs.getInt("user_score")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("getUserById 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("DB 연결 성공: getAllUsers 메서드 실행 중"); // 디버깅 메시지
            while (rs.next()) {
                users.add(new User(
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getInt("course_uid"),
                        rs.getInt("category_uid"),
                        rs.getInt("user_status"),
                        rs.getInt("user_score")
                ));
            }
        } catch (SQLException e) {
            System.err.println("getAllUsers 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE user SET user_password = ?, course_uid = ?, category_uid = ?, user_status = ?, user_score = ? WHERE user_id = ?";
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("DB 연결 성공: updateUser 메서드 실행 중"); // 디버깅 메시지
            ps.setString(1, user.getUserPassword());
            ps.setInt(2, user.getCourseUid());
            ps.setObject(3, user.getCategoryUid() == 0 ? null : user.getCategoryUid());
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
    public void deleteUser(String userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("DB 연결 성공: deleteUser 메서드 실행 중"); // 디버깅 메시지
            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("deleteUser 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

	@Override
	public boolean isValidUser(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}
}
