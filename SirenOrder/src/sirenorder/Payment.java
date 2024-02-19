package sirenorder;

import java.io.IOException;
import java.util.Scanner;

public class Payment {
	private String userId;
	private int money = 10000;

	public Payment(String userId, int money) {
		this.userId = userId;
		this.money = money;
	}

	public String getUserId() {
		return userId;
	}
	
	public int savedMoney() {
		return money;
	}
	
	public String toString() {
        return userId + "님의 남은 금액: " + money;
    }
//	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//	
//        int payChoice = 0;
//        do {
//			String pay;
//			System.out.println("결제 수단을 선택해주세요:");
//			System.out.println("1. 스타벅스 카드");
//			System.out.println("2. 신용카드");
//			System.out.println("3. 쿠폰");
//			payChoice = scanner.nextInt();
//			switch (payChoice) {
//			case 1:
//				pay = "스타벅스 카드";
//				break;
//			case 2:
//				pay = "신용카드";
//				break;
//			case 3:
//				pay = "쿠폰";
//				break;
//			default:
//				System.out.println("잘못된 선택입니다.");
//				scanner.nextLine();
//				break;
//			}
//        }while(payChoice!=1 || payChoice!=2 || payChoice!=3);
		
//        Payment payment = new Payment("userId", 10000);
//	}
}
