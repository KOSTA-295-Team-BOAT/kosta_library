package business.dto;

/**
 *과정별 추천도서와 관련된 DTO 클래스
 *@author 황태윤
 *@since 2025.03.27
 */
public class CourseRecommend {
    private int courseRecommendUid;
    private int courseUid;
    private int bookUid;

    // 기본 생성자
    public CourseRecommend() {}

    // 모든 필드를 포함한 생성자
    public CourseRecommend(int courseRecommendUid, int courseUid, int bookUid) {
        this.courseRecommendUid = courseRecommendUid;
        this.courseUid = courseUid;
        this.bookUid = bookUid;
    }

    // Getter 및 Setter 메서드
    public int getCourseRecommendUid() {
        return courseRecommendUid;
    }

    public void setCourseRecommendUid(int courseRecommendUid) {
        this.courseRecommendUid = courseRecommendUid;
    }

    public int getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(int courseUid) {
        this.courseUid = courseUid;
    }

    public int getBookUid() {
        return bookUid;
    }

    public void setBookUid(int bookUid) {
        this.bookUid = bookUid;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "CourseRecommend{" +
                "courseRecommendUid=" + courseRecommendUid +
                ", courseUid=" + courseUid +
                ", bookUid=" + bookUid +
                '}';
    }
}
