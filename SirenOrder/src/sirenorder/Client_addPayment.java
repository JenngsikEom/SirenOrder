package sirenorder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client_addPayment {
	public static void main(String[] args) {
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
            switch (choice) {
                case 1:
                    coffeeType = "아메리카노";
                    break;
                case 2:
                    coffeeType = "라떼";
                    break;
                case 3:
                    coffeeType = "에스프레소";
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 기본값인 아메리카노로 주문합니다.");
                    coffeeType = "아메리카노";
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
			
            // 추가
            scanner.nextLine(); // 개행문자 처리
            
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
    				
    				break;
    			case 2:
    				pay = "신용카드";
    				break;
    			case 3:
    				pay = "쿠폰";
    				break;
    			default:
    				System.out.println("잘못된 선택입니다.");
    				scanner.nextLine();
    				break;
    			}
            }while(payChoice!=1 || payChoice!=2 || payChoice!=3);
            
            
            Order order = new Order(coffeeType, size, syrupAdded, userId);
            Payment payment = new Payment(userId, 10000);
            
            Socket socket = new Socket("localhost", 8080);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println(order.toString());
            out.println(payment.toString());

            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
