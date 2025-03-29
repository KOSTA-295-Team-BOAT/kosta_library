package repository.dao;

import business.dto.Course;
import java.util.List;

/**
 * 과정과 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface CourseDao {
    // 과정 추가
    void addCourse(Course course);

    // 과정 ID로 조회
    Course getCourseById(int courseUid);

    // 모든 과정 조회
    List<Course> getAllCourses();

}
