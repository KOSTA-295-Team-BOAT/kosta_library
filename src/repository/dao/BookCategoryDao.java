package repository.dao;

import business.dto.BookCategory;
import exception.SearchWrongException;

import java.util.List;

/**
 * 카테고리와 관련된 DAO 기능을 정의하는 인터페이스. 데이터베이스에 데이터를 요청한다.
 * @author 황태윤
 * @since 2025.03.27
 */
public interface BookCategoryDao {
    // 카테고리 추가
    void addCategory(BookCategory category);

    // 카테고리 ID로 조회
    BookCategory getCategoryById(int categoryId) throws SearchWrongException;

    // 모든 카테고리 조회
    List<BookCategory> getAllCategories() throws SearchWrongException;

    // 카테고리 이름으로 조회
    BookCategory getCategoryByName(String categoryName);

    // 카테고리 업데이트
    void updateCategory(BookCategory category);

    // 카테고리 삭제
    void deleteCategory(int categoryId);
}
