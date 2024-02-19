package sirenorder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

//import org.json.simple.JSONObject;



public class Server {
	public static void main(String[] args) {
		final int PORT = 8888; //포트번호
		Hashtable<String, Socket>clientHt = new Hashtable<>(); //접속자관리 해시테이블
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			String mainThreadName = Thread.currentThread().getName();
			while(true) {
				System.out.println("사이렌 오더 서버 시작 Client접속을 기다립니다. ");
				Socket socket = serverSocket.accept();
				
			//	WorkerThread wt = new WorkerThread(socket, clientHt);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

class WokerThread extends Thread {
	private Socket socket;
	private Hashtable<String, Socket>ht;
	
	
	public WokerThread(Socket socekt, Hashtable<String, Socket>ht) {
		this.socket =socket;
		this.ht =ht;
	}
	public void run() {
		try {
			InetAddress inetAddr = socket.getInetAddress();
			System.out.printf("<서버-%s>%s로부터 접속했습니다.\n", getName(), inetAddr.getHostAddress());
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			while(true) {
				
				String line = br.readLine();
				
				if(line == null)
					break;
				
			//	JSONObject packetObj = new JSONObject(line);
				
			//	processPacket(packetObj);
				
			}
		} catch (Exception e) {
			System.out.printf("<서버-%s>%s\n", getName(), e.getMessage());
		}
	}
}


