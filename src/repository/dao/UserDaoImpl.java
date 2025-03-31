package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.dto.User;
import repository.util.DbManager;

/**
 * 사용자 관련 DAO 구현 클래스
 */
public class UserDaoImpl implements UserDao {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user (user_id, user_password, user_name, course_uid, user_status, user_score) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        if (userId == null) {
            System.err.println("getUserById: userId가 null입니다.");
            return null;
        }
        
        String sql = "SELECT u.*, COALESCE(uf.category_uid, 0) as category_uid FROM user u " +
                    "LEFT JOIN user_favorite uf ON u.user_id = uf.user_id " +
                    "WHERE u.user_id = ?";
        
        try (Connection con = DbManager.getConnection()) {
            if (con == null) {
                System.err.println("데이터베이스 연결 실패");
                return null;
            }
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int categoryUid = rs.getInt("category_uid");
                        return new User(
                            rs.getString("user_id"),
                            rs.getString("user_password"),
                            rs.getInt("course_uid"),
                            categoryUid,
                            rs.getInt("user_status"),
                            rs.getInt("user_score"),
                            rs.getString("user_name")
                        );
                    }
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
        String sql = "SELECT u.*, COALESCE(uf.category_uid, 0) as category_uid FROM user u " +
                    "LEFT JOIN user_favorite uf ON u.user_id = uf.user_id";
        System.out.println("getAllUsers 메서드 시작");
        
        try (Connection con = DbManager.getConnection()) {
            if (con == null) {
                System.err.println("데이터베이스 연결 실패");
                return users;
            }
            
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                
                int count = 0;
                while (rs.next()) {
                    users.add(new User(
                        rs.getString("user_id"),
                        rs.getString("user_password"),
                        rs.getInt("course_uid"),
                        rs.getInt("category_uid"),  // 추가
                        rs.getInt("user_status"),
                        rs.getInt("user_score"),
                        rs.getString("user_name")
                    ));
                    count++;
                }
                System.out.println("조회된 총 사용자 수: " + count);
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
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
}