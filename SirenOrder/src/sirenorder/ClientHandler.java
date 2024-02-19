package sirenorder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // 클라이언트와의 통신을 위한 입력 스트림 생성
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

            // 클라이언트로부터 메시지 수신
            String clientMessage;
            while ((clientMessage = dis.readUTF()) != null) {
                System.out.println("클라이언트 메시지: " + clientMessage);
            }

            // 클라이언트와의 연결 종료
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}