package business.dto;

/**
 *이용자 정보와 관련된 DTO 클래스 
 *@author 황태윤
 *@since 2025.03.27
 */
public class User {
    // 필드 추가
    private String userId;
    private String userPassword;
    private int courseUid;
    private int categoryUid; // null 불가능
    private int userStatus;
    private int userScore;

    // 기본 생성자
    public User() {}

    // 모든 필드를 포함한 생성자
    public User(String userId, String userPassword, int courseUid, int categoryUid, int userStatus, int userScore) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.courseUid = courseUid;
        this.categoryUid = categoryUid;
        this.userStatus = userStatus;
        this.userScore = userScore;
    }

    // Getter 및 Setter 메서드
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(int courseUid) {
        this.courseUid = courseUid;
    }

    public int getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(int categoryUid) {
        this.categoryUid = categoryUid;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", courseUid=" + courseUid +
                ", categoryUid=" + categoryUid +
                ", userStatus=" + userStatus +
                ", userScore=" + userScore +
                '}';
    }
}
