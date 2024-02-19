package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		
		String IP = "127.0.0.1";
		int PORT = 8888;
		String id = "";
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		Scanner scan = new Scanner(System.in);
		
		System.out.printf("기본 IP=%s, 기본 PORT=%d\n", IP, PORT);
		System.out.print("IP, Port 설정필요하십니까?(y/yes/예, 아니면 그냥 Enter) >> ");
		String answer = scan.nextLine();
		if(answer.equals("y") || answer.equals("yes") || answer.equals("예")) {
			System.out.print("접속할 IP >> ");
			IP = scan.nextLine();
			System.out.print("접속할 Port >> ");
			PORT = Integer.parseInt(scan.nextLine());
		}
		
		try {
			// 서버를 주소를 찾아서 연결
			socket = new Socket(IP, PORT);
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			// 별도의 worker thread를 생성해서 서버로부터의 수신을 담당한다.
			ReceiveThread rt = new ReceiveThread(br);
			rt.start();
			
			// main thread는 서버에 전송을 담당한다.
			// 1) id를 서버에 등록한다
			
			id = sendId(scan, pw);
			Thread.sleep(300);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(socket!=null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}		
			if(scan!=null) scan.close();
		}
		
	}
	
	
	
public static String sendId(Scanner sc, PrintWriter pw) {
	//JsonChatClient.mainState = ClientState.STATE_ID;
	System.out.print("당신의 id 입력 >> ");
	String id = sc.nextLine();
	
	// 프로토콜(약속)에 의한 요청 패킷 구성
	//JSONObject idObj = new JSONObject();
	//idObj.put("cmd", "ID");
	//idObj.put("id", id);
	// 문자열 변환
	//String packet = idObj.toString();
//	pw.println(packet);
	//pw.flush();
	
	return id;
}
}

class ReceiveThread extends Thread{
	private BufferedReader br = null;
	
	public ReceiveThread(BufferedReader br) {
		this.br = br;
	}
	@Override
	public void run() {
		try {			
			while(true) {
				String packet = br.readLine();
				if(packet==null)
					break;
				
			//	JSONObject packetObj = new JSONObject(packet);
			//	processPacket(packetObj);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}


