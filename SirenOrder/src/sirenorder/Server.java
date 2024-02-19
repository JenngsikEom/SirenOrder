package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            while (true) {
                // 클라이언트로부터 주문 정보를 읽어옴
                String orderInfo = reader.readLine();
                if (orderInfo == null)
                    break;
                System.out.println(orderInfo);

                // 클라이언트에게 주문 정보를 다시 전송
                printWriter.println(orderInfo);
                printWriter.flush();
            }

            printWriter.close();
            reader.close();

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
