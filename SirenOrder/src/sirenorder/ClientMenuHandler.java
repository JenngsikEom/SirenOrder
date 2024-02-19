package sirenorder;

import java.util.Scanner;

public class ClientMenuHandler {
    private Scanner scanner;

    public ClientMenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("--------------------------------------");
            System.out.println("1. 커피 주문");
            System.out.println("2. 전체 채팅");
            System.out.println("3. 1:1 채팅");
            System.out.println("4. 종료");
            System.out.print("원하는 기능을 선택하세요: ");
            
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            int menuChoice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (menuChoice) {
                case 1:
                    // TODO: 커피 주문 기능 호출
                    break;

                case 2:
                    AllChatClient.showAllChatMenu(scanner); // 전체 채팅 메뉴 호출
                    break;

                case 3:
                    // TODO: 1:1 채팅 기능 호출
                    break;

                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    return;

                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }
}