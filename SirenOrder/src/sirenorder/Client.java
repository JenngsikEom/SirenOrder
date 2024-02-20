package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {

	// 서버 주소와 포트 번호 설정
	private static final String serverAddress = "localhost";
	private static final int serverPort = 9999;

	private static String loggedInUserId;
	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;
	
	
	public static void main(String[] args) throws ParseException {
		try {
			Socket socket = new Socket(serverAddress, serverPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			System.out.println("사이렌오더 서버에 연결되었습니다.");

			// 사용자 입력을 통해 로그인, 회원가입, 종료 중 하나를 선택할 수 있는 메뉴 반복
			Scanner scanner = new Scanner(System.in);
			
			while (true) {
				displayMenu();

				String userInput = scanner.nextLine();

				switch (userInput) {
				case "1":
					login(scanner); // 로그인 처리
					break;
				case "2":
					signup(scanner); // 회원가입 처리
					break;
				case "3":
					System.out.println("사이렌오더 서버 연결을 종료합니다."); // 프로그램 종료
					return;
				default:
					System.out.println("잘못된 입력입니다. 다시 선택해주세요."); // 잘못된 입력 처리
				}
			}
		} catch (IOException e) {
			System.out.println("서버 연결에 실패했습니다: " + e.getMessage()); // 서버 연결 실패 처리
		}
	}

	// 사용자에게 메뉴 옵션을 표시하는 메서드
	private static void displayMenu() {
		System.out.println("1. [로그인]");
		System.out.println("2. [회원가입]");
		System.out.println("3. [종료]");
		System.out.println("메뉴를 선택하세요. >> ");
	}

	// 로그인 기능을 처리하는 메서드
	private static void login(Scanner scanner) throws IOException, ParseException {
		JSONObject json = new JSONObject();
		json.put("type", "login");
		json.put("userid", promptForInput(scanner, "아이디를 입력하세요>>"));
		json.put("password", promptForInput(scanner, "패스워드를 입력하세요>>"));

		out.println(json.toString()); // 서버의 로그인 요청 전송
		handleServerResponse(scanner); // 로그인 응답 처리

	}

	// 회원가입 기능을 처리하는 메서드
	private static void signup(Scanner scanner) throws IOException, ParseException {
		JSONObject json = new JSONObject();
		json.put("type", "signup");
		json.put("userid", promptForInput(scanner, "사용할 아이디를 입력하세요"));
		json.put("password", promptForInput(scanner, "사용할 패스워드를 입력하세요"));

		out.println(json.toString());
		handleServerResponse(scanner); // 서버로부터의 응답 처리
	}

	// 사용자로부터 입력을 요청하는 메서드
	private static String promptForInput(Scanner scanner, String prompt) throws IOException {
		System.out.println(prompt);
		return scanner.nextLine(); // Scnner를 사용하여 사용자 입력 받기
	}

	private static void handleServerResponse(Scanner scanner) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		String responseString = in.readLine();

		if (responseString != null) {
			JSONObject response = (JSONObject) parser.parse(responseString);
			System.out.println("서버 응답" + response.toJSONString());

			// 로그인 응답 처리
			if (response.containsKey("로그인상태")) {
				String loginStatus = (String) response.get("로그인상태");
				if (loginStatus.equals("성공")) {
					loggedInUserId = (String) response.get("userid");
					//로그인에 성공했을 때 클라이언트 정보를 서버에 전송
					sendClientInfoToServer();
					handleLoginSuccess(scanner);
				} else {
					System.out.println("로그인에 실패했습니다. 다시 시도해주세요.");
				}
			} else {
				System.out.println("알 수 없는 응답 타입입니다.");
			}
		} else {
			System.out.println("서버로부터 응답을 받지 못했습니다.");
		}
	}
	// 로그인에 성공한 후 클라이언트 정보를 서버에 전송하는 메서드
    private static void sendClientInfoToServer() {
        JSONObject json = new JSONObject();
        json.put("type", "client_info");
        json.put("userid", loggedInUserId);
        out.println(json.toString());
    }

	private static void handleLoginSuccess(Scanner scanner) throws IOException, ParseException {

		displayPostLoginMenu(); // 로그인 성공 후 메뉴 표시
		String userInput = scanner.nextLine(); // 사용자 입력 받기

		// 로그인 응답처리

		switch (userInput) {
		case "1":
			orderCoffee(scanner, out, in); // 커피 주문 처리
			break;
		case "2":
			pointCharge(scanner, out); // 포인트충전
			break;
		case "3":
			oneChat(scanner, out, loggedInUserId); // 채팅방 이동 처리
			break;
		case "4":
			System.out.println("서비스를 종료합니다.");
			break;
		default:
			System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
			break;
		}
	}
	private static void displayPostLoginMenu() {
		System.out.println("\n로그인중입니다.");
		System.out.println("1. 커피 주문");
		System.out.println("2. 포인트 충전");
		System.out.println("3. 채팅방 이동");
		System.out.println("4. 종료");
		System.out.println("메뉴를 선택하세요. >> ");

	}

	private static void orderCoffee(Scanner scanner, PrintWriter out, BufferedReader in)
			throws IOException, ParseException {
		CoffeeOrder coffeeOrder = new CoffeeOrder(); // CoffeeOrder 객체 생성
		coffeeOrder.orderCoffee(scanner, out, in); // 커피 주문 메서드 호출
	}

	private static void pointCharge(Scanner scanner, PrintWriter out) {

	}

	private static void oneChat(Scanner scanner,  PrintWriter out, String userId) {

		try (Socket socket = new Socket(serverAddress, serverPort);
				PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {
			
			
			
			//채팅방으로 이동
			OneChat oneChat = new OneChat(scanner, out, userId); 
			oneChat.run(); // OneChat 객체의 run 메서드 실행

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	  private static void handleSignupResponse() throws IOException, ParseException {
	        JSONParser parser = new JSONParser();
	        String responseString = in.readLine();
	        if (responseString != null) {
	            JSONObject response = (JSONObject) parser.parse(responseString);
	            System.out.println("회원가입 응답: " + response.toJSONString());
	        } else {
	            System.out.println("서버로부터 응답을 받지 못했습니다.");
	        }
	    }
	}
