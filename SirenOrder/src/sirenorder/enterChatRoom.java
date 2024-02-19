package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

public class enterChatRoom {
	
	private static void entherChatRoom(BufferedReader stdIn, PrintWriter out) throws IOException {
		//서버로 채팅방 입장을 요청하는 메시지 전송
		JSONObjcet json = new JSONObject();
		json.put("type", "enterChatRoom"); //메시지 타입 설정
		out.println(json.toJSONString()); //서버에 메시지 전송
		
		//서버로부터의 응답수신
		String response = stdIn.readLine();
		System.out.println( ); //서버로부터 응답 출력
		
       

    


        // 핸들러 실행 메서드
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true); // 출력 스트림 생성
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // 입력 스트림 생성

                String message;
                while ((message = in.readLine()) != null) { // 클라이언트로부터 메시지를 계속 받음
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject jsonMessage = (JSONObject) parser.parse(message); // JSON 형식의 메시지를 파싱

                        String messageType = (String) jsonMessage.get("type"); // 메시지 유형 확인
                        if ("message".equals(messageType)) { // 만약 메시지 유형이 "message"이면
                            String content = (String) jsonMessage.get("content"); // 메시지 내용 추출
                            System.out.println("클라이언트로부터 메시지 수신: " + content); // 받은 메시지 출력

                            // 모든 클라이언트에게 메시지 전송
                            for (ChatHandler client : clients) {
                                if (client != this) { // 자기 자신을 제외하고
                                    client.sendMessage(content); // 모든 클라이언트에게 메시지 전송
                                }
                            }
                        } else {
                            System.out.println("잘못된 메시지 형식입니다.");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 메시지를 클라이언트에게 전송하는 메서드
        public void sendMessage(String content) {
            JSONObject jsonMessage = new JSONObject(); // JSON 객체 생성
            jsonMessage.put("type", "broadcast"); // 메시지 유형 설정
            jsonMessage.put("content", content); // 메시지 내용 설정
            out.println(jsonMessage.toJSONString()); // JSON 형식의 메시지 전송
        }
    }

} 
