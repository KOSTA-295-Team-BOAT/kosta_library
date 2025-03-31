package business.dto;

/**
 *도서 대여 상세정보와 관련된 DTO 클래스 
 *@author 황태윤
 *@since 2025.03.27
 */
public class RentDetail {
    private int rentDetailUid;
    private int bookUid;
    private int rentUid;
    private int rentReturnState;
    private String rentReturnDue;

    // 기본 생성자
    public RentDetail() {}

    // 모든 필드를 포함한 생성자
    public RentDetail(int rentDetailUid, int bookUid, int rentUid, int rentReturnState, String rentReturnDue) {
        this.rentDetailUid = rentDetailUid;
        this.bookUid = bookUid;
        this.rentUid = rentUid;
        this.rentReturnState = rentReturnState;
        this.rentReturnDue = rentReturnDue;
    }

    // Getter 및 Setter 메서드
    public int getRentDetailUid() {
        return rentDetailUid;
    }

    public void setRentDetailUid(int rentDetailUid) {
        this.rentDetailUid = rentDetailUid;
    }

    public int getBookUid() {
        return bookUid;
    }

    public void setBookUid(int bookUid) {
        this.bookUid = bookUid;
    }

    public int getRentUid() {
        return rentUid;
    }

    public void setRentUid(int rentUid) {
        this.rentUid = rentUid;
    }

    public int getRentReturnState() {
        return rentReturnState;
    }

    public void setRentReturnState(int rentReturnState) {
        this.rentReturnState = rentReturnState;
    }

    public String getRentReturnDue() {
        return rentReturnDue;
    }

    public void setRentReturnDue(String rentReturnDue) {
        this.rentReturnDue = rentReturnDue;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "RentDetail{" +
                "rentDetailUid=" + rentDetailUid +
                ", bookUid=" + bookUid +
                ", rentUid='" + rentUid + '\'' +
                ", rentReturnState=" + rentReturnState +
                ", rentReturnDue='" + rentReturnDue + '\'' +
                '}';
    }
}
