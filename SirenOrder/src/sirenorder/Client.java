package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
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

            Order order = new Order(coffeeType, size, syrupAdded, userId, coffeeOption, consumptionMethod);

            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(order.toString());

            // 서버로부터 다시 주문 정보를 받아옴
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("주문 정보: " + response);

            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
