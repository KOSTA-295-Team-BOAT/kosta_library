package presentation.controller;

import business.dto.Book;
import business.dto.BookRent;
import business.dto.User;
import business.service.book.BookRentService;
import exception.SearchWrongException;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
import util.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class BookCartController {
    private static final List<Book> bookCart = new ArrayList<>();
    private static final int MAX_RENT_LIMIT = 5;
    private static int currentRentalCount = 0;

    private final BookRentService rentService = new BookRentService();

    // 현재 유저의 대여 중인 권수 갱신
    public void refreshRentalCount() {
        String userId = SessionManager.getCurrentUserId();
        if (userId != null) {
            try {
				currentRentalCount = rentService.getRentDetailByUserId(userId).size();
			} catch (SearchWrongException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
        }
    }

    // 도서 추가 (상세보기 화면에서 호출)
    public void addToCart(Book book) {
        int total = currentRentalCount + bookCart.size();
        if (total >= MAX_RENT_LIMIT) {
            System.out.println("최대 대여 가능 권수를 초과할 수 없습니다. (최대 " + MAX_RENT_LIMIT + "권)");
            return;
        }

        if (bookCart.contains(book)) {
            System.out.println("이미 북카트에 담긴 도서입니다.");
            return;
        }

        bookCart.add(book);
        System.out.println("'" + book.getBookName() + "'이(가) 북카트에 추가되었습니다.");
    }

    // 도서 제거
    public void removeFromCart(Book book) {
        if (bookCart.remove(book)) {
            System.out.println("'" + book.getBookName() + "'이(가) 북카트에서 제거되었습니다.");
        } else {
            System.out.println("해당 도서는 북카트에 없습니다.");
        }
    }

    // 북카트 비우기
    public void clearCart() {
        bookCart.clear();
        System.out.println("북카트가 비워졌습니다.");
    }

    // 북카트 목록 반환
    public List<Book> getCart() {
        return new ArrayList<>(bookCart);
    }

    // 북카트 출력
    public void viewCart() {
        System.out.println("현재 북카트 목록:");
        if (bookCart.isEmpty()) {
            System.out.println(" - 북카트가 비어 있습니다.");
        } else {
            for (int i = 0; i < bookCart.size(); i++) {
                Book book = bookCart.get(i);
                System.out.printf(" %d. %s / %s\n", i + 1, book.getBookName(), book.getBookAuthor());
            }
        }
    }

    // 대여 확정 처리
    public void confirmRental() {
        String userId = SessionManager.getCurrentUserId();
        if (userId == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }

        if (bookCart.isEmpty()) {
            System.out.println("북카트가 비어 있어 대여할 수 없습니다.");
            return;
        }

        UserDao userDao = new UserDaoImpl();
        User currentUser = userDao.getUserById(userId);
        if (currentUser == null) {
            System.out.println("사용자 정보를 불러올 수 없습니다.");
            return;
        }

        try {
            BookController bookController = new BookController();
            BookRent rent = bookController.rentBooks(currentUser, bookCart);

            if (rent != null) {
                System.out.println("도서 대여가 완료되었습니다.");
                bookCart.clear();
            } else {
                System.out.println("도서 대여에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (Exception e) {
            System.out.println("도서 대여 중 예외 발생: " + e.getMessage());
        }
    }
}