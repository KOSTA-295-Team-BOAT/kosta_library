package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.dto.Book;
import repository.util.DbManager;

public class BookDaoImpl implements BookDao {

	PreparedStatement ps = null;

	@Override
	public void addBook(Book book) {
		// 도서정보 추가 CRUD는 MVP에서는 구현하지 않는다
	}

	@Override
	public void updateBookStatus(Connection con, Book book, int bookStatus) throws SQLException {
		// 트랜잭션 동작을 위해 Connection은 외부로부터(Service로부터) 받아오는 구조...
		// 닫는것도 커넥션을 시작한 Service에서 한다
		try {
			String query = "update book set book_status = ? where book_uid=?";
			ps = con.prepareStatement(query);
			ps.setInt(1, bookStatus);
			ps.setInt(2, book.getBookUid());
			int result = ps.executeUpdate();
			if (result == 0) {
				throw new SQLException("수정 실패");
			}
		} finally {
			DbManager.close(null, ps, null); // ps, rs는 닫지만 con은 닫지않음. rs는 auto-close
		}

	}

	@Override
	public void deleteBook(int bookUid) {
		// 도서정보 삭제 CRUD는 MVP에서는 구현하지 않는다
	}

	@Override
	public List<Book> getAllBooks() throws SQLException {
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book");

			rs = ps.executeQuery();

			if (rs.next()) {
				book = new Book(rs.getInt("book_uid"), rs.getString("book_name"), rs.getString("book_author"),
						rs.getString("book_publisher"), rs.getInt("category_uid"), rs.getInt("category_uid2"),
						rs.getInt("category_uid3"), rs.getString("book_store_date"), rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}

	@Override
	public List<Book> getBookByBookName(String name) throws SQLException {
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_name like ?");
			ps.setString(1, "%" + name + "%"); // setString을 한번 감싸서 like에 들어갈 조건식으로 만들어준다
			rs = ps.executeQuery();

			while (rs.next()) {
				book = new Book(rs.getInt("book_uid"), rs.getString("book_name"), rs.getString("book_author"),
						rs.getString("book_publisher"), rs.getInt("category_uid"), rs.getInt("category_uid2"),
						rs.getInt("category_uid3"), rs.getString("book_store_date"), rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}

	@Override
	public List<Book> getBookByBookPublisher(String name) throws SQLException {
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_publisher like ?");
			ps.setString(1, "%" + name + "%"); // setString을 한번 감싸서 like에 들어갈 조건식으로 만들어준다
			rs = ps.executeQuery();

			while (rs.next()) {
				book = new Book(rs.getInt("book_uid"), rs.getString("book_name"), rs.getString("book_author"),
						rs.getString("book_publisher"), rs.getInt("category_uid"), rs.getInt("category_uid2"),
						rs.getInt("category_uid3"), rs.getString("book_store_date"), rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}

	@Override
	public List<Book> getBookByAuthor(String name) throws SQLException {
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_author like ?");
			ps.setString(1, "%" + name + "%"); // setString을 한번 감싸서 like에 들어갈 조건식으로 만들어준다
			rs = ps.executeQuery();

			while (rs.next()) {
				book = new Book(rs.getInt("book_uid"), rs.getString("book_name"), rs.getString("book_author"),
						rs.getString("book_publisher"), rs.getInt("category_uid"), rs.getInt("category_uid2"),
						rs.getInt("category_uid3"), rs.getString("book_store_date"), rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}

	@Override
	public Book getBookById(int bookUid) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_id=?");
			ps.setInt(1, bookUid);
			rs = ps.executeQuery();

			if (rs.next()) {
				book = new Book(rs.getInt("book_uid"), rs.getString("book_name"), rs.getString("book_author"),
						rs.getString("book_publisher"), rs.getInt("category_uid"), rs.getInt("category_uid2"),
						rs.getInt("category_uid3"), rs.getString("book_store_date"), rs.getInt("book_status"));

			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return book;
	}

	@Override
	public void updateBook(Book book) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> getMostRentedBooksByUserInterest(String userId) {
		List<Book> books = new ArrayList<>();
		String sql = 
			"SELECT b.*, COUNT(*) AS rent_count " +
			"FROM book b " +
			"JOIN user_favorite uf ON b.category_uid = uf.category_uid " +
			"JOIN rent_detail rd ON b.book_uid = rd.book_uid " +
			"JOIN book_rent br ON rd.rent_uid = br.rent_uid " +
			"WHERE uf.user_id = ? " +
			"AND br.rent_date >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
			"GROUP BY b.book_uid " +
			"ORDER BY rent_count DESC";

		try (Connection conn = DbManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Book book = new Book();
				book.setBookUid(rs.getInt("book_uid"));
				book.setBookName(rs.getString("book_name"));
				book.setBookAuthor(rs.getString("book_author"));
				book.setBookPublisher(rs.getString("book_publisher"));
				book.setBookStatus(rs.getInt("book_status"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

}
