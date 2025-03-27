package business.dto;

/**
 *도서 예약과 관련된 DTO 클래스
 *@author 황태윤
 *@since 2025.03.27
 */
public class BookReservation {
    private int reservationUid;
    private int bookUid;
    private int reservationStatus;
    private String reservationDate;
    private String reservationDue;
    private String userId;

    // 기본 생성자
    public BookReservation() {}

    // 모든 필드를 포함한 생성자
    public BookReservation(int reservationUid, int bookUid, int reservationStatus, String reservationDate, String reservationDue, String userId) {
        this.reservationUid = reservationUid;
        this.bookUid = bookUid;
        this.reservationStatus = reservationStatus;
        this.reservationDate = reservationDate;
        this.reservationDue = reservationDue;
        this.userId = userId;
    }

    // Getter 및 Setter 메서드
    public int getReservationUid() {
        return reservationUid;
    }

    public void setReservationUid(int reservationUid) {
        this.reservationUid = reservationUid;
    }

    public int getBookUid() {
        return bookUid;
    }

    public void setBookUid(int bookUid) {
        this.bookUid = bookUid;
    }

    public int getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(int reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationDue() {
        return reservationDue;
    }

    public void setReservationDue(String reservationDue) {
        this.reservationDue = reservationDue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "BookReservation{" +
                "reservationUid=" + reservationUid +
                ", bookUid=" + bookUid +
                ", reservationStatus=" + reservationStatus +
                ", reservationDate='" + reservationDate + '\'' +
                ", reservationDue='" + reservationDue + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
