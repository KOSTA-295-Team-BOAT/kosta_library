package business.dto;

/**
 *도서 카테고리와 관련된 DTO 클래스 
 *@author 황태윤
 *@since 2025.03.27
 */
public class BookCategory {
    // 필드 추가
    private int categoryUid;
    private String categoryName;

    public int getCategoryUid() {
        return this.categoryUid;
    }

    public void setCategoryUid(int categoryUid) {
        this.categoryUid = categoryUid;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // toString 메서드(디버깅용)
    @Override
    public String toString() {
        return "BookCategory [categoryUid=" + categoryUid + ", categoryName=" + categoryName + "]";
    }

    // 기본 생성자
    public BookCategory() {}

    // 모든 필드를 포함한 생성자
    public BookCategory(int categoryUid, String categoryName) {
        this.categoryUid = categoryUid;
        this.categoryName = categoryName;
    }



}
