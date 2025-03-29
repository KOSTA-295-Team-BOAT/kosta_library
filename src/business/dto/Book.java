package business.dto;

/**
 *도서 정보와 관련된 DTO 클래스 
 *@author 황태윤
 *@since 2025.03.27
 */
public class Book {
    private int bookUid;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private int categoryUid;
    private int categoryUid2; 
    private int categoryUid3; 
    private String bookStoreDate;
    private int bookStatus;

    // 기본 생성자
    public Book() {}

    // 모든 필드를 포함한 생성자
    public Book(int bookUid, String bookName, String bookAuthor, String bookPublisher, int categoryUid, int categoryUid2, int categoryUid3, String bookStoreDate, int bookStatus) {
        this.bookUid = bookUid;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.categoryUid = categoryUid;
        this.categoryUid2 = categoryUid2;
        this.categoryUid3 = categoryUid3;
        this.bookStoreDate = bookStoreDate;
        this.bookStatus = bookStatus;
    }

    // Getter 및 Setter 메서드
    public int getBookUid() {
        return bookUid;
    }

    public void setBookUid(int bookUid) {
        this.bookUid = bookUid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public int getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(int categoryUid) {
        this.categoryUid = categoryUid;
    }

    public int getCategoryUid2() { 
        return categoryUid2;
    }

    public void setCategoryUid2(int categoryUid2) {
        this.categoryUid2 = categoryUid2;
    }

    public int getCategoryUid3() {
        return categoryUid3;
    }

    public void setCategoryUid3(int categoryUid3) {
        this.categoryUid3 = categoryUid3;
    }

    public String getBookStoreDate() {
        return bookStoreDate;
    }

    public void setBookStoreDate(String bookStoreDate) {
        this.bookStoreDate = bookStoreDate;
    }

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "Book{" +
                "bookUid=" + bookUid +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", categoryUid=" + categoryUid +
                ", categoryUid2=" + categoryUid2 +
                ", categoryUid3=" + categoryUid3 +
                ", bookStoreDate='" + bookStoreDate + '\'' +
                ", bookStatus=" + bookStatus +
                '}'+'\n';
    }
}
