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

	@Override
	public void addBook(Book book) {
		// 도서정보 추가 CRUD는 MVP에서는 구현하지 않는다
	}


	@Override
	public void updateBook(Book book) {
		// 도서정보 수정 CRUD는 MVP에서는 구현하지 않는다

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
				book = new Book(rs.getInt("book_uid"),
						rs.getString("book_name"),
						rs.getString("book_author"),
						rs.getString("book_publisher"),
						rs.getInt("category_uid"),
						rs.getInt("category_uid2"),
						rs.getInt("category_uid3"),
						rs.getString("book_store_date"),
						rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}

	@Override
	public List<Book> getBookByBookName(String name) throws SQLException{
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_name like ?");
			ps.setString(1, "%"+name+"%"); //setString을 한번 감싸서 like에 들어갈 조건식으로 만들어준다
			rs = ps.executeQuery();

			while(rs.next()){
				book = new Book(rs.getInt("book_uid"),
						rs.getString("book_name"),
						rs.getString("book_author"),
						rs.getString("book_publisher"),
						rs.getInt("category_uid"),
						rs.getInt("category_uid2"),
						rs.getInt("category_uid3"),
						rs.getString("book_store_date"),
						rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}

	@Override
	public List<Book> getBookByBookPublisher(String name) throws SQLException{
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_publisher like ?");
			ps.setString(1, "%"+name+"%"); //setString을 한번 감싸서 like에 들어갈 조건식으로 만들어준다
			rs = ps.executeQuery();

			while(rs.next()){
				book = new Book(rs.getInt("book_uid"),
						rs.getString("book_name"),
						rs.getString("book_author"),
						rs.getString("book_publisher"),
						rs.getInt("category_uid"),
						rs.getInt("category_uid2"),
						rs.getInt("category_uid3"),
						rs.getString("book_store_date"),
						rs.getInt("book_status"));
				returnBooks.add(book);
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return returnBooks;
	}
	@Override  
	public List<Book> getBookByAuthor(String name) throws SQLException{
		List<Book> returnBooks = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement("select * from book where book_author like ?");
			ps.setString(1, "%"+name+"%"); //setString을 한번 감싸서 like에 들어갈 조건식으로 만들어준다
			rs = ps.executeQuery();

			while(rs.next()){
				book = new Book(rs.getInt("book_uid"),
						rs.getString("book_name"),
						rs.getString("book_author"),
						rs.getString("book_publisher"),
						rs.getInt("category_uid"),
						rs.getInt("category_uid2"),
						rs.getInt("category_uid3"),
						rs.getString("book_store_date"),
						rs.getInt("book_status"));
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
				book = new Book(rs.getInt("book_uid"),
						rs.getString("book_name"),
						rs.getString("book_author"),
						rs.getString("book_publisher"),
						rs.getInt("category_uid"),
						rs.getInt("category_uid2"),
						rs.getInt("category_uid3"),
						rs.getString("book_store_date"),
						rs.getInt("book_status"));

			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return book;
	}


}
