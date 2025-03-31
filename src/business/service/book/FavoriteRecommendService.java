package business.service.book;

import java.util.List;
import business.dto.Book;
import repository.dao.BookDao;
import repository.dao.BookDaoImpl;

public class FavoriteRecommendService {
    private final BookDao bookDao = new BookDaoImpl();

    public List<Book> getRecommendedBooksByFavorite(String userId) {
        return bookDao.getMostRentedBooksByUserInterest(userId);
    }
}
