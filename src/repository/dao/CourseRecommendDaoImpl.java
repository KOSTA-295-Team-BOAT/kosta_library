package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.dto.Book;
import business.dto.CourseRecommend;
import repository.util.DbManager;

public class CourseRecommendDaoImpl implements CourseRecommendDao {

    @Override
    public void addCourseRecommend(CourseRecommend courseRecommend) {
        // TODO Auto-generated method stub

    }

    @Override
    public CourseRecommend getCourseRecommendById(int courseRecommendUid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CourseRecommend> getAllCourseRecommends() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateCourseRecommend(CourseRecommend courseRecommend) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCourseRecommend(int courseRecommendUid) {
        // TODO Auto-generated method stub

    }

    public List<Book> getRecommendedBooksByUserId(String userId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.* FROM course_recommend cr " +
                    "JOIN book b ON cr.book_uid = b.book_uid " +
                    "JOIN user u ON cr.course_uid = u.course_uid " +
                    "WHERE u.user_id = ?";
                    
        try (Connection conn = DbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookUid(rs.getInt("book_uid"));
                book.setBookName(rs.getString("book_name"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookPublisher(rs.getString("book_publisher"));
                book.setBookStatus(rs.getInt("book_status"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
