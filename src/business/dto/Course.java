package business.dto;

/**
 *과정 정보와 관련된 DTO 클래스 
 *@author 황태윤
 *@since 2025.03.27
 */
public class Course {
    // 필드 추가
    private int courseUid;
    private String courseName;
    private String course_open;
    private String courseGraduateDate;

    public int getCourseUid() {
        return this.courseUid;
    }

    public void setCourseUid(int courseUid) {
        this.courseUid = courseUid;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourse_open() {
        return this.course_open;
    }

    public void setCourse_open(String course_open) {
        this.course_open = course_open;
    }

    public String getCourseGraduateDate() {
        return this.courseGraduateDate;
    }

    public void setCourseGraduateDate(String courseGraduateDate) {
        this.courseGraduateDate = courseGraduateDate;
    }

    //toString 메서드(디버깅용)
    @Override
    public String toString() {
        return "Course [courseUid=" + courseUid + ", courseName=" + courseName + ", course_open=" + course_open + ", courseGraduateDate=" + courseGraduateDate + "]";
    }


    //기본 생성자
    public Course() {}

    //모든 필드를 포함한 생성자
    public Course(int courseUid, String courseName, String course_open, String courseGraduateDate) {
        this.courseUid = courseUid;
        this.courseName = courseName;
        this.course_open = course_open;
        this.courseGraduateDate = courseGraduateDate;
    }

}
