package repository.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/** 
 * DB와의 커넥션 정보를 담당하는 기능 클래스
 * @author kosta
 */
public class DbManager {
	private static Properties proFile = new Properties();

	/** 
	 * Static 초기화 : Properties 파일 및 드라이버 정보 로드
	 * @author kosta
	 */
	static {
		try {
			// 외부 properteis파일 로딩하기
			proFile.load(new FileInputStream("resources/dbInfo.properties"));
			// proFile.load(new FileInputStream("board.properties"));

			Class.forName(proFile.getProperty("driverName"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 접속 정보와 관련된 Properties 객체 반환하는 메소드.
	 * @return (Properties) Properties 객체
	 * @author kosta 
	 */
	public static Properties getProFile() {
		return proFile;
	}

	/**
	 * 커넥션 정보 반환하는 메소드. Properties 파일로부터 읽어온 정보를 기반으로 연결된 커넥션을 리턴한다.
	 * @return (DriverManager) 커넥션 정보
	 * @throws SQLException : Exception 정보를 DAO로 반환된다.
	 * @author kosta 
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				proFile.getProperty("url"), 
				proFile.getProperty("userName"),
				proFile.getProperty("userPass"));
	}

	/**
	 * 커넥션을 닫아주는 메소드
	 * @param con (Connection) Connection 객체
	 * @param st  (Statement) Statement 객체
	 * @param rs  (ResultSet) ResultSet 객체
	 * @author kosta 
	 */
	public static void close(Connection con, Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}