package repository.dao;

import business.dto.BookCategory;
import repository.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryDaoImpl implements BookCategoryDao {
	@Override
	public BookCategory getCategoryById(int categoryId) {
		String sql = "SELECT * FROM book_category WHERE category_uid = ?";
		try (Connection con = DbManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, categoryId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new BookCategory(rs.getInt("category_uid"), rs.getString("category_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	// ...other methods...

	@Override
	public void addCategory(BookCategory category) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<BookCategory> getAllCategories() {
		List<BookCategory> categories = new ArrayList<>();
		String sql = "SELECT * FROM book_category";
		
		try (Connection con = DbManager.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				categories.add(new BookCategory(
					rs.getInt("category_uid"),
					rs.getString("category_name")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories; // 항상 List 반환 (비어있더라도 null이 아님)
	}

	@Override
	public BookCategory getCategoryByName(String categoryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCategory(BookCategory category) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
	}
}
