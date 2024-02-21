package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import sirenorder.Payment;



public class Client {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE"; // 변경될 수 있는 정보는 외부 설정에서 관리하는 것이 좋습니다.
    private static final String USER = "c##salmon"; // 데이터베이스 사용자 이름
    private static final String PASSWORD = "1234"; // 데이터베이스 비밀번호
	
    public static void main(String[] args) {
    	Connection conn = null;
    	try {
    		// JDBE Driver 등록
    		Class.forName("oracle.jdbc.OracleDriver");
    		
    		// 연결하기
    		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.33:1521:XE", "c##salmon", "1234");
    		
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		if(conn != null) {
    			try {
    				// 연결 끊기
    				conn.close();
    				System.out.println("연결 끊기");
    			}catch(SQLException e) {
    				
    			}
    		}
    	}
    	
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("아이디를 입력하세요:");
            String userId = scanner.nextLine();

            System.out.println("커피 종류를 선택해주세요:");
            System.out.println("1. 아메리카노");
            System.out.println("2. 라떼");
            System.out.println("3. 에스프레소");
            int choice = scanner.nextInt();
            String coffeeType;
            int price; // 커피 가격 
         
            switch (choice) {
                case 1:
                    coffeeType = "아메리카노";
                    price = 4500; // 아메리카노 가격
                    break;
                case 2:
                    coffeeType = "라떼";
                    price = 5000; // 라떼 가격
                    break;
                case 3:
                    coffeeType = "에스프레소";
                    price = 4000; // 에스프레소 가격
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 기본값인 아메리카노로 주문합니다.");
                    coffeeType = "아메리카노";
                    // 추가
                    price = 4500; //추가: 기본값인 아메리카노 가격
                    break;
            }

            scanner.nextLine(); // 개행문자 처리

            System.out.println("커피 사이즈를 선택해주세요:");
            System.out.println("1. 작은(S)");
            System.out.println("2. 중간(M)");
            System.out.println("3. 큰(L)");
            int sizeChoice = scanner.nextInt();
            String size;
            switch (sizeChoice) {
                case 1:
                    size = "작은(S)";
                    break;
                case 2:
                    size = "중간(M)";
                    break;
                case 3:
                    size = "큰(L)";
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 기본값인 중간(M) 사이즈로 주문합니다.");
                    size = "중간(M)";
                    break;
            }

            scanner.nextLine(); // 개행문자 처리

            System.out.println("시럽을 추가하시겠습니까? (y/n)");
            boolean syrupAdded = scanner.nextLine().equalsIgnoreCase("y");

            System.out.println("커피 옵션을 선택해주세요:");
            System.out.println("1. Ice");
            System.out.println("2. Hot");
            int optionChoice = scanner.nextInt();
            String coffeeOption;
            switch (optionChoice) {
                case 1:
                    coffeeOption = "Ice";
                    break;
                case 2:
                    coffeeOption = "Hot";
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 기본값인 Ice로 설정합니다.");
                    coffeeOption = "Ice";
                    break;
            }

            scanner.nextLine(); // 개행문자 처리

            System.out.println("취식 방법을 선택해주세요:");
            System.out.println("1. 매장이용");
            System.out.println("2. 테이크아웃");
            int methodChoice = scanner.nextInt();
            String consumptionMethod;
            switch (methodChoice) {
                case 1:
                    consumptionMethod = "매장이용";
                    break;
                case 2:
                    consumptionMethod = "테이크아웃";
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 기본값인 매장이용으로 설정합니다.");
                    consumptionMethod = "매장이용";
                    break;
            }

            int result = 0;
            int payChoice = 0;
            do {
                String pay;
                System.out.println("결제 수단을 선택해주세요:");
                System.out.println("1. 스타벅스 카드");
                System.out.println("2. 신용카드");
                System.out.println("3. 쿠폰");
                payChoice = scanner.nextInt();
                switch (payChoice) {
                    case 1:
                        pay = "스타벅스 카드";
                        System.out.println("결제가 완료되었습니다.");
                        result = Payment.leftMoney(price);   // static을 사용하면 클래스 이름을 통해 언제든지 호출 가능
                        System.out.println("잔액 : " + result); 
                        break;
                    case 2:
                        pay = "신용카드";
                        System.out.println("결제가 완료되었습니다.");
                        break;
                    case                        3:
                        pay = "쿠폰";
                        System.out.println("결제가 완료되었습니다.");
                        break;
                    default:
                        System.out.println("잘못된 선택입니다.");
                        scanner.nextLine();
                        break;
                }
            } while (payChoice != 1 && payChoice != 2 && payChoice != 3);

            Order order = new Order(coffeeType, size, syrupAdded, userId, coffeeOption, consumptionMethod);
            Payment payment = new Payment(userId, 10000);

            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(order.toString());
            out.println(payment.toString());

            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

