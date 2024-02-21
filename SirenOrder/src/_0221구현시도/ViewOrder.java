package _0221구현시도;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewOrder {
	// Oracle 데이터베이스 연결 정보
    private static final String DB_URL = "jdbc:oracle:thin:@192.168.0.33:1521:XE"; // Oracle 서버 주소와 포트
    private static final String USER = "c##salmon"; // 데이터베이스 사용자 이름
    private static final String PASSWORD = "1234"; // 데이터베이스 비밀번호
    private static final Logger logger = Logger.getLogger(CoffeeOrder.class.getName());

    // 주문 조회 기능
    public void viewOrders() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Oracle JDBC 드라이브 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 데이터베이스 연결
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // SQL 쿼리 작성
            String query = "SELECT * FROM ORDERS";
            statement = connection.prepareStatement(query);

            // 쿼리 실행 및 결과 처리
            resultSet = statement.executeQuery();

            // 주문 내역 출력
            System.out.println("주문 내역:");
            while (resultSet.next()) {
                String orderDetails = resultSet.getString("ORDER_DETAILS");
                System.out.println(orderDetails);
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "주문 조회 중 오류 발생", e);
        } finally {
            // 리소스 해제
            closeResources(resultSet, statement, connection);
        }
    }

    // 리소스 해제 메서드
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
