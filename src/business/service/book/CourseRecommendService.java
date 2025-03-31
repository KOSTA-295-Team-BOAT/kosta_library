package business.service.book;

import java.util.List;
import business.dto.Book;
import repository.dao.CourseRecommendDao;
import repository.dao.CourseRecommendDaoImpl;
import repository.dao.BookDao;
import repository.dao.BookDaoImpl;

public class CourseRecommendService {
    private final CourseRecommendDao courseRecommendDao = new CourseRecommendDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();

    public List<Book> getRecommendedBooksByCourse(String userId) {
        return courseRecommendDao.getRecommendedBooksByUserId(userId);
    }
}
