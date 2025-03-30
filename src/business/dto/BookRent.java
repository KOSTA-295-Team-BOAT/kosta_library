package business.dto;

/**
 *도서 대여와 관련된 DTO 클래스
 *@author 황태윤
 *@since 2025.03.27
 */
public class BookRent {
    private int rentUid;
    private String userId;
    private String rentDate;
    private int rentStatus;
    private String rentDue;

    // 기본 생성자
    public BookRent() {}

    // 모든 필드를 포함한 생성자
    public BookRent(int rentUid, String userId, String rentDate, int rentStatus, String rentDue) {
        this.rentUid = rentUid;
        this.userId = userId;
        this.rentDate = rentDate;
        this.rentStatus = rentStatus;
        this.rentDue = rentDue;
    }

    // Getter 및 Setter 메서드
    public int getRentUid() {
        return rentUid;
    }

    public void setRentUid(int rentUid) {
        this.rentUid = rentUid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public int getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(int rentStatus) {
        this.rentStatus = rentStatus;
    }

    public String getRentDue() {
        return rentDue;
    }

    public void setRentDue(String rentDue) {
        this.rentDue = rentDue;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "BookRent{" +
                "rentUid='" + rentUid + '\'' +
                ", userId='" + userId + '\'' +
                ", rentDate='" + rentDate + '\'' +
                ", rentStatus=" + rentStatus +
                ", rentDue='" + rentDue + '\'' +
                '}';
    }
}
