package sirenorder;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientChatReceiver implements Runnable {
    private Socket socket;

    public ClientChatReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                String receivedMessage = dis.readUTF();
                System.out.println(receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}