package sirenorder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoffeeOrder {

	// Oracle 데이터베이스 연결 정보
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE"; // Oracle 서버 주소와 포트
	private static final String USER = "c##salmon"; // 데이터베이스 사용자 이름
	private static final String PASSWORD = "1234"; // 데이터베이스 비밀번호
	private static final Logger logger = Logger.getLogger(Login.class.getName());

	// 데이터베이스에서 커피 메뉴를 가져오는 메서드
	public List<CoffeeMenu> getCoffeeMenuFromDatabase() {
		List<CoffeeMenu> coffeeMenuList = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Oracle JDBC 드라이브 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 데이터베이스 연결
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

			// SQL 쿼리 작성
			String query = "SELECT * FROM CoffeeMenu";
			statement = connection.prepareStatement(query);

			// 쿼리 실행 및 결과 처리
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				int price = resultSet.getInt("price");
				boolean isIced = resultSet.getInt("is_iced") == 1;
				boolean hasSyrup = resultSet.getInt("syrup_option") == 1;
				boolean isTakeout = resultSet.getInt("is_takeout") == 1;

				// CoffeeMenu 객체 생성 및 리스트에 추가
				CoffeeMenu coffeeMenu = new CoffeeMenu(name, price, isIced, hasSyrup, isTakeout);
				coffeeMenuList.add(coffeeMenu);
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.log(Level.SEVERE, "커피 메뉴 조회 중 오류 발생", e);
		} finally {
			// 리소스 해제
			closeResources(resultSet, statement, connection);
		}

		return coffeeMenuList;
	}

	public class CoffeeMenu {
		private String name;
		private int price;
		private boolean isIced;
		private boolean hasSyrup;
		private boolean isTakeout;
		
		public CoffeeMenu(String name, int price, boolean isIced, boolean hasSyrup, boolean isTakeout) {
			this.name = name;
			this.price = price;
			this.isIced = isIced;
			this.hasSyrup = hasSyrup;
			this.isTakeout =isTakeout;
			
		}
		

		// 생성자, getter 및 setter 메서드 구현
	}

	// 서버와의 통신을 위한 메서드
	public void communicateWithServer() {
		// 서버와 통신하는 코드 작성
	}

	// 커피를 주문하는 메서드
	public void orderCoffee(String menuName) {
		// 커피를 주문하는 코드 작성
	}

	private static void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "리소스 해제 중 오류 발생", e);
		}
	}
}
