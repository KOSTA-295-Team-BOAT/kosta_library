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

public class BookRentService {
	public static final int RENT_DAY = 5;
	public static final int BOOK_RENT_ABLE = 0;
	public static final int BOOK_RENT_NOW = 1;
	public static final int BOOK_RENT_MAX = 5;

	BookDao bookDao = new BookDaoImpl();
	BookRentDao dao = new BookRentDaoImpl();
	RentDetailDao detailDao = new RentDetailDaoImpl();
	List<RentDetail> rentDetailList = new ArrayList<>();

	Connection con = null;

	public BookRent setRentInfo(User user) {
		BookRent rent = new BookRent();
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String due = LocalDateTime.now().plusDays(RENT_DAY).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));

		rent.setUserId(user.getUserId());
		rent.setRentStatus(0);
		rent.setRentDate(now);
		rent.setRentDue(due);
		return rent;
	}

	public RentDetail setRentDetail(BookRent rent, Book book) {
		RentDetail rentDetail = new RentDetail();
		rentDetail.setRentUid(rent.getRentUid());
		rentDetail.setBookUid(book.getBookUid());
		rentDetail.setRentReturnState(0);
		rentDetail.setRentReturnDue(rent.getRentDue());
		return rentDetail;
	}

	public BookRent rentBooks(User user, List<Book> books) throws DmlException {
		Connection con = null;
		BookRent rent;

		try {
			int currentRentCount = getRentDetailByUserId(user.getUserId()).size();
			if (currentRentCount + books.size() > BOOK_RENT_MAX) {
				throw new DmlException("최대 대여 가능 권수를 초과했습니다. 현재 대여 중: " + currentRentCount + "권, 요청: " + books.size()
						+ "권, 제한: " + BOOK_RENT_MAX + "권");
			}

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

		if (book.getBookStatus() == BOOK_RENT_NOW)
			throw new DmlException("대여중인 도서입니다.");

		int currentRentCount = getRentDetailByUserId(user.getUserId()).size();
		if (currentRentCount + 1 > BOOK_RENT_MAX) {
			throw new DmlException(
					"최대 대여 가능 권수를 초과했습니다. 현재 대여 중: " + currentRentCount + "권, 요청: 1권, 제한: " + BOOK_RENT_MAX + "권");
		}

		try {
			con = DbManager.getConnection();
			con.setAutoCommit(false);
			rent = dao.addBookRent(con, setRentInfo(user));
			RentDetail rentDetail = setRentDetail(rent, book);
			detailDao.addRentDetail(con, rentDetail);
			bookDao.updateBookStatus(con, book, BOOK_RENT_NOW);
			con.commit();
			return rent;

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
				throw new DmlException("오류가 발생했습니다. (롤백 오류)");
			}
			e.printStackTrace();
			throw new DmlException("오류가 발생했습니다.");

		} finally {
			if (con != null)
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DmlException("오류가 발생했습니다.(커넥션 오류)");
				}
		}
	}

	public boolean rentBook(String userId, int bookUid) {
		try {
			int currentRentCount = getRentDetailByUserId(userId).size();
			if (currentRentCount + 1 > BOOK_RENT_MAX) {
				System.out.println(
						"최대 대여 가능 권수를 초과했습니다. 현재 대여 중: " + currentRentCount + "권, 요청: 1권, 제한: " + BOOK_RENT_MAX + "권");
				return false;
			}

			User user = new User();
			user.setUserId(userId);

			Book book = bookDao.getBookById(bookUid);
			if (book == null) {
				return false;
			}

			BookRent result = rentOneBook(user, book);
			return result != null;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<RentDetail> getRentDetailByUserId(String userId) throws SearchWrongException {
		Connection con = null;
		List<RentDetail> rentDetailList = new ArrayList<>();
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
