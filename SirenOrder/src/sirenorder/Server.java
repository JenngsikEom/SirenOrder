package sirenorder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        System.out.println("--------------------------------------");
        System.out.println("서버 종료 시 q를 입력하고 Enter키를 입력하세요.");
        System.out.println("--------------------------------------");

        // TCP 서버 시작
        startServer();
    }

    public static void startServer() {
        // 작업 스레드 정의
        Thread thread = new Thread(() -> {
            try {
                // ServerSocket 생성 및 port 바인딩
                serverSocket = new ServerSocket(50001);
                System.out.println("[서버] 시작됨");

                // 연결 수락 및 데이터 통신
                while (true) {
                    System.out.println("\n[서버] 연결 요청을 기다림\n");
                    // 연결 수락
                    Socket socket = serverSocket.accept();

                    // 클라이언트 핸들러 생성 및 스레드 시작
                    Thread clientHandlerThread = new Thread(new ClientHandler(socket));
                    clientHandlerThread.start();
                }
            } catch (IOException e) {
                System.out.println("[서버] " + e.getMessage());
            }
        });
        // 스레드 시작
        thread.start();
    }
}