package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class OneChat implements Runnable { // Runnable 인터페이스 구현
	
	private Scanner scanner;
	private BufferedReader stdIn; // 표준 입력 스트림
	private PrintWriter out; // 출력 스트림
	private String userId; // 사용자 ID 추가
	

	// 생성자
	public  OneChat(Scanner scanner, PrintWriter out, String userId) {
		this.scanner = scanner;
		
		this.out = out;
		this.userId = userId; // 사용자 ID 설정
		
	}
	

	@Override
	public void run() {
		try {
			
			//채팅방 입장 메시지 출력
			System.out.println(userId + "님, 사이렌오더 채팅방에 오신걸 환영합니다.(종료:'quit'입력");
			
						
			// 서버로 채팅방 입장을 요청하는 메시지 전송
			JSONObject json = new JSONObject();
			json.put("type", "enterChatRoom"); // 메시지 타입 설정
			json.put("userId", userId); // 사용자 아이디 전송
			out.println(json.toJSONString()); // 서버에 메시지 전송

			// 서버로부터의 응답수신
			String response;
			while ((response = stdIn.readLine()) != null) {
				System.out.println(response); // 서버로부터 응답 출력

				// 사용자 입력받기
				System.out.println(userId + ": ");
				String userInput = stdIn.readLine();
				// sendMessage("사이렌오더 채팅방에 오신걸 환영합니다.");

				if ("quit".equals(userInput)) {
					System.out.println("채팅이 종료되었습니다.");
					break;
				} else {

					// 그 외의 입력은 서버로 전송
					sendMessage(userInput);
				}
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
