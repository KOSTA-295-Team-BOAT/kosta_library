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
import exception.SearchWrongException;
import repository.dao.BookDao;
import repository.dao.BookDaoImpl;
import repository.dao.BookRentDao;
import repository.dao.BookRentDaoImpl;
import repository.dao.RentDetailDao;
import repository.dao.RentDetailDaoImpl;
import repository.dao.UserDao;
import repository.dao.UserDaoImpl;
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
		String due = LocalDateTime.now().plusDays(RENT_DAY).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
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

		if (book.getBookStatus() == 1)
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
			}
			e.printStackTrace();
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

	// 메소드 있는지 확인도 안하고 GPT가 맘대로 만든 코드... 리팩토링 할 때 날리자. 일단 동작은 하니까 두고 리팩토링할때 날리면 됨
	// TODO 이 메소드 삭제하고 뷰 리팩토링 해야 함
	public boolean rentBook(String userId, int bookUid) {
		try {
			// 사용자와 도서 정보로 User와 Book 객체 생성
			User user = new User();
			user.setUserId(userId);

			Book book = bookDao.getBookById(bookUid);
			if (book == null) {
				return false;
			}

			// 기존 rentOneBook 메서드 활용
			BookRent result = rentOneBook(user, book);
			return result != null;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<RentDetail> getRentDetailByUserId(String userId) throws SearchWrongException {
		Connection con = null;
		List<RentDetail> rentDetailList = new ArrayList<RentDetail>();
		try {
			con = DbManager.getConnection();
			rentDetailList = detailDao.getRentDetailByUserId(con, userId);
			if (rentDetailList.isEmpty())
				throw new SearchWrongException("현재 대여중인 도서가 없습니다.");
			else
				return rentDetailList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SearchWrongException("오류가 발생했습니다.");
		}

	}

	public RentDetail returnOneBook(int rentDetailUid) throws DmlException {
		Connection con = null;
		RentDetail rentDetail = null;

		try {
			con = DbManager.getConnection();
			con.setAutoCommit(false);

			rentDetail = detailDao.getRentDetailById(con, rentDetailUid);
			if (rentDetail == null) {
				throw new DmlException("해당 대여 정보가 존재하지 않습니다.");
			}

			rentDetail.setRentReturnState(1);
			detailDao.updateRentDetail(con, rentDetail);

			Book book = bookDao.getBookById(rentDetail.getBookUid());
			if (book != null) {
				bookDao.updateBookStatus(con, book, BOOK_RENT_ABLE);
			}

			BookRent rentInfo = dao.getBookRentById(con, rentDetail.getRentUid());
			String userId = rentInfo.getUserId();
			LocalDateTime rentDue = LocalDateTime.parse(rentInfo.getRentDue(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime now = LocalDateTime.now();

			UserDao userDao = new UserDaoImpl();
			User user = userDao.getUserById(con, userId);
			if (user == null) {
				throw new DmlException("사용자 정보를 찾을 수 없습니다.");
			}

			int currentScore = user.getUserScore();
			int updatedScore;

			if (now.isAfter(rentDue)) {
				updatedScore = Math.max(0, currentScore - 10);
			} else {
				updatedScore = currentScore + 5;
			}

			userDao.updateUserScore(con, userId, updatedScore);

			List<RentDetail> remains = detailDao.getNotReturnedDetailByRentUid(con, rentDetail.getRentUid());
			if (remains.isEmpty()) {
				// 모든 도서 반납 완료 → rent_status와 rent_return_date 갱신
				int rentStatus = 0;
				if (now.isAfter(rentDue))
					rentStatus = 3;
				else
					rentStatus = 1; // 3: 연체반납, 1: 정상반납
				String returnDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				dao.updateRentStatusAndReturnDate(con, rentDetail.getRentUid(), rentStatus, returnDate);
			}

			con.commit();
			return rentDetail;

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
				throw new DmlException("오류가 발생했습니다. (롤백 오류)");
			}
			e.printStackTrace();
			throw new DmlException("도서를 반납하는 중 오류가 발생했습니다.");
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DmlException("오류가 발생했습니다. (커넥션 오류)");
				}
			}
		}
	}
}
