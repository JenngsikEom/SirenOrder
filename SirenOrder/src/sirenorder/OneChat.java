package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class OneChat implements Runnable { // Runnable 인터페이스 구현
	
	private Scanner scanner;
	private BufferedReader stdIn; // 표준 입력 스트림
	private PrintWriter out; // 출력 스트림
	private String userId; // 사용자 ID 추가
	private Map<String, PrintWriter> clientMap; // 클라이언트 맵
	

	// 생성자
	public OneChat(Scanner scanner, BufferedReader stdIn, PrintWriter out, String userId, Map<String, PrintWriter> clientMap) {
	    this.scanner = scanner;
	    this.stdIn = stdIn; // stdIn 변수 초기화
	    this.out = out;
	    this.userId = userId;
	    this.clientMap = clientMap;
	}

	@Override
	public void run() {
		try {
			
			//채팅방 입장 메시지 출력
			System.out.println(userId + "님, 사이렌오더 채팅방에 오신걸 환영합니다.(종료:'quit'입력");
			
						
			

			// 서버로부터의 응답수신
			String response;
			while ((response = stdIn.readLine()) != null) {
				System.out.println(response); // 서버로부터 응답 출력

				// 사용자가 입력한 채팅 메시지 받기
				System.out.println(userId + ": ");
				String message = scanner.nextLine();
				
				
				// 서버에서 받은 응답 출력
	            String response = in.readLine();
	            System.out.println(response);

	            // 사용자가 'quit'을 입력하면 채팅 종료
                if (message.equals("quit")) {
                    System.out.println("채팅이 종료되었습니다.");
                    break;
				} 
                
             // 서버로 채팅 메시지 전송
                JSONObject json = new JSONObject();
                json.put("type", "chat");
                json.put("userId", userId);
                json.put("message", message);
                out.println(json.toJSONString());
                
               

				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 메시지를 클라이언트에게 전송하는 메서드
	public void sendMessage(String content) {
		// 메시지에 사용자 ID 추가
		content = "[" + userId + "]" + content;
		
		JSONObject jsonMessage = new JSONObject(); // JSON 객체 생성
		jsonMessage.put("type", "broadcast"); // 메시지 유형 설정
		jsonMessage.put("sender", userId); // 보낸 사람 아이디설정
		jsonMessage.put("content", content); // 메시지 내용 설정
		out.println(jsonMessage.toJSONString()); // JSON 형식의 메시지 전송
	}
}
