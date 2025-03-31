package business.service.book;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import business.dto.Book;
import business.dto.RentDetail;
import business.dto.User;
import business.dto.BookRent;
import exception.DmlException;
import repository.dao.BookDao;
import repository.dao.BookDaoImpl;
import repository.dao.BookRentDao;
import repository.dao.BookRentDaoImpl;
import repository.dao.RentDetailDao;
import repository.dao.RentDetailDaoImpl;
import repository.util.DbManager;

/**
 * @author 도서대여기능 비즈니스로직
 */
public class BookRentService {
	public static final int RENT_DAY = 5;
	public static final int BOOK_RENT_ABLE = 0;
	public static final int BOOK_RENT_NOW = 1;
	
	BookDao bookDao = new BookDaoImpl();
	BookRentDao dao = new BookRentDaoImpl();
	RentDetailDao detailDao = new RentDetailDaoImpl();
	List<RentDetail> rentDetailList = new ArrayList<RentDetail>();

	Connection con = null;
	// 2개 이상의 쿼리를 묶어서 처리하는 트랜잭션이 발생하므로, 요청이 묶이는 비즈니스 로직단에서 커넥션을 생성해서 DAO 방향으로 쏜다.

	public BookRent setRentInfo(User user) { // 대여정보 DTO 생성하는 메소드
		BookRent rent = new BookRent();
		// 대여 날짜 및 반납 날짜 기록을 위한 날짜 받아오기
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String due = LocalDateTime.now().plusDays(RENT_DAY).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		// 대여ID는 Auto-Increment로 DB에 자동 기록된다. 여기서 따로 초기화해주지 않는다. (null로 넘어감)

		rent.setUserId(user.getUserId());
		rent.setRentStatus(0);
		rent.setRentDate(now);
		rent.setRentDue(due);
		return rent;
	}

	public RentDetail setRentDetail(BookRent rent, Book book) {
		RentDetail rentDetail = new RentDetail();
		rentDetail.setRentUid(rent.getRentUid()); // 대여건에 대한 rentUid를 받아서 적용
		rentDetail.setBookUid(book.getBookUid());
		rentDetail.setRentReturnState(0);
		rentDetail.setRentReturnDue(rent.getRentDue());
		return rentDetail;

	}

	public BookRent rentBooks(User user, List<Book> books) throws DmlException {
		Connection con = null;
		BookRent rent;

		try {
			con = DbManager.getConnection();
			con.setAutoCommit(false);
			
			rent = dao.addBookRent(con, setRentInfo(user)); 

			for (Book book : books) {
				RentDetail detail = setRentDetail(rent, book);
				detailDao.addRentDetail(con, detail);
				bookDao.updateBookStatus(con, book, BOOK_RENT_NOW);
			}

			con.commit();
			return rent;

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
					throw new DmlException("오류가 발생했습니다. (롤백 오류)");
				}
			}
			throw new DmlException("오류가 발생했습니다.");

		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
					throw new DmlException("오류가 발생했습니다. (커넥션 오류)");
				}
			}
		}
	}

	public BookRent rentOneBook(User user, Book book) throws Exception {
		BookRent rent;
		
		
		
		if(book.getBookStatus()==1)
			throw new DmlException("대여중인 도서입니다...");
		
		
		try {
			con = DbManager.getConnection();
			con.setAutoCommit(false);
			rent = dao.addBookRent(con, setRentInfo(user)); // 커넥션을 같이 넘김, 성공했다면 rent_uid를 받아옴
			RentDetail rentDetail = setRentDetail(rent, book); // rentDetail 생성
			detailDao.addRentDetail(con, rentDetail);
			bookDao.updateBookStatus(con, book, BOOK_RENT_NOW);			
			con.commit();
			return rent; // 처리가 끝난 결과를

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback(); // 커넥션이 있는 상태에서 아무 예외 발생 시 모든 쿼리 롤백
			} catch (SQLException e2) {
				e2.printStackTrace();
				throw new DmlException("오류가 발생했습니다. (롤백 오류)"); // SQLException을 한번 감쌈
			}e.printStackTrace();
			throw new DmlException("오류가 발생했습니다."); // 기타 다른 에러 전부 묶어서 전달

		} finally {
			if (con != null)
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DmlException("오류가 발생했습니다.(커넥션 오류)"); // 커넥션 닫다가 오류가 발생해도 감싸서 던짐
				}

		}
	}
}
