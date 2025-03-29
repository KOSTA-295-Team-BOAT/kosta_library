package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.dto.Course;
import repository.util.DbManager;

/**
 * 과정과 관련된 DAO 기능을 정의하는 인터페이스를 구현하는 클래스
 */
public class CourseDaoImpl implements CourseDao {
    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO course (course_name, course_open, course_graduate_date) VALUES (?, ?, ?)";
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getCourse_open());
            ps.setString(3, course.getCourseGraduateDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course getCourseById(int courseUid) {
        String sql = "SELECT * FROM course WHERE course_uid = ?";
        try (Connection con = DbManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseUid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                        rs.getInt("course_uid"),
                        rs.getString("course_name"),
                        rs.getInt("course_open"),
                        rs.getString("course_graduate_date")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";
        try (Connection con = DbManager.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new Course(
                    rs.getInt("course_uid"),
                    rs.getString("course_name"),
                    rs.getInt("course_open"),
                    rs.getString("course_graduate_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    
}
