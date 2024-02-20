package dbTest;

import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

public class PointCharge {
	// Oracle 데이터베이스 연결 정보
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE"; // Oracle 서버 주소와 포트
	private static final String USER = "c##salmon"; // 데이터베이스 사용자 이름
	private static final String PASSWORD = "1234"; // 데이터베이스 비밀번호
	private static final Logger logger = Logger.getLogger(Login.class.getName());
	public static int point = 10000; // 추가: 스타벅스 카드 내 잔액 임의 지정
	
	public Int point(int point) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        JSONObject jsonResponse = new JSONObject();
    
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 데이터베이스 연결
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            // SQL 쿼리 작성: 사용자 이름과 비밀번호를 기준으로 사용자 존재 여부 확인
            String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            // PreparedStatement 사용하여 SQL 쿼리 실행
            statement = connection.prepareStatement(query);
            statement.setint(3, point);
            
            // 쿼리 실행 및 스타벅스 카드 조회
            resultSet = statement.executeQuery();
            if(resultSet.next() && resultSet.getInt(3) == 3) {
            	point= resultSet.getInt(3);
            	jsonResponse.put("스타벅스 카드가 존재합니다.(잔액 : " + point +")");
        	} else {
        		jsonResponse.put("스타벅스 카드가 존재하지 않습니다.");
        	}
        } catch(ClassNotFoundException | SQLException e) {
                logger.log(Level.SEVERE, "스타벅스 카드 조회 중 오류 발생", e);
        }
        
    }return point

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
