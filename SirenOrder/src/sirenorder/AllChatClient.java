package sirenorder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class AllChatClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClientMenuHandler menuHandler = new ClientMenuHandler(sc);
        menuHandler.showMenu();
        sc.close();
    }

    public static void showAllChatMenu(Scanner scanner) {
        try {
            Socket socket = new Socket("localhost", 50001);
            System.out.println("[클라이언트] 연결 성공");

            // 사용자 닉네임 입력
            System.out.print("닉네임을 입력하세요: ");
            String nickname = scanner.nextLine();

            // 전체 채팅 시작 메시지 전송
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("[" + nickname + "] 님이 전체 채팅에 참여하였습니다. 종료를 원하시면 q를 입력해주세요.");

            // 전체 채팅 수신 스레드 시작
            Thread receiveThread = new Thread(new ClientChatReceiver(socket));
            receiveThread.start();

            // 전체 채팅 메시지 입력 및 전송
            while (true) {
                System.out.print("[전체 채팅] 메시지 입력 : ");

                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("q")) {
                    // 사용자가 q를 입력하면 전체 채팅 종료 메시지 전송 후 종료
                    dos.writeUTF("[" + nickname + "] 님이 전체 채팅을 종료하였습니다.");
                    break;
                }

                // 전체 채팅 메시지 전송
                dos.writeUTF("[" + nickname + "]: " + message);
            }

            socket.close();
            System.out.println("[클라이언트] 연결 끊김");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}