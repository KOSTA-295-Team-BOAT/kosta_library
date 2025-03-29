package repository.dao;

import business.dto.CourseRecommend;
import java.util.List;

/**
 * 과정별 추천도서와 관련된 DAO 기능을 정의하는 인터페이스
 */
public interface CourseRecommendDao {
    // 추천 도서 추가
    void addCourseRecommend(CourseRecommend courseRecommend);

    // 추천 도서 ID로 조회
    CourseRecommend getCourseRecommendById(int courseRecommendUid);

    // 모든 추천 도서 조회
    List<CourseRecommend> getAllCourseRecommends();

    // 추천 도서 업데이트
    void updateCourseRecommend(CourseRecommend courseRecommend);

    // 추천 도서 삭제
    void deleteCourseRecommend(int courseRecommendUid);
}
