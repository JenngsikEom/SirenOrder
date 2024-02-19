package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("서버가 시작되었습니다. 클라이언트의 연결을 기다립니다...");

            Socket socket = serverSocket.accept();
            System.out.println("클라이언트가 연결되었습니다.");

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String orderInfo = reader.readLine();
            System.out.println("주문이 접수되었습니다: " + orderInfo);

            // 클라이언트에게 다시 주문을 받을 수 있도록 메시지를 전송
            System.out.println("다시 주문해주세요.");

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
