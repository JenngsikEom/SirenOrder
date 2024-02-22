package _0221구현시도;

import java.io.IOException;
import java.util.Scanner;

public class Payment {
	// 결제 메서드 추가
    public static void payment() throws IOException {
        Scanner scanner = new Scanner(System.in);

        int payChoice = 0;
        do {
            String pay;
            System.out.println("결제 수단 :");
            System.out.println("1. 스타벅스 카드");
            System.out.println("2. 신용카드");
            System.out.println("3. 쿠폰");
            System.out.println("메뉴를 선택하세요. >> ");
            payChoice = scanner.nextInt();
            switch (payChoice) {
                case 1:
                    pay = "스타벅스 카드";
                    System.out.println(pay+ "결제가 완료되었습니다.");
                    break;
                case 2:
                    pay = "신용카드";
                    System.out.println(pay+ "결제가 완료되었습니다.");
                    break;
                case 3:
                    pay = "쿠폰";
                    System.out.println(pay+ "결제가 완료되었습니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    scanner.nextLine();
                    break;
            }
        } while (payChoice != 1 && payChoice != 2 && payChoice != 3);
    }
}
