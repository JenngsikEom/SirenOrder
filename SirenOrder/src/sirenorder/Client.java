package sirenorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {

	// 서버 주소와 포트 번호 설정
	private static final String serverAddress = "localhost";
	private static final int serverPort = 9999;

	public static void main(String[] args) {
		try (Socket socket = new Socket(serverAddress, serverPort);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("사이렌오더 서버에 연결되었습니다.");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 사용자 입력을 통해 로그인, 회원가입, 종료 중 하나를 선택할 수 있는 메뉴 반복
			while (true) {
				displayMenu();
				String userInput = stdIn.readLine();
				switch (userInput) {
				case "1":
					login(stdIn, out, in); // 로그인 처리
					break;
				case "2":
					signup(stdIn, out, in); // 회원가입 처리
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
	private static void login(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
		JSONObject json = new JSONObject();
		json.put("type", "login");
		json.put("userid", promptForInput(stdIn, "아이디를 입력하세요>>"));
		json.put("password", promptForInput(stdIn, "패스워드를 입력하세요>>"));
		out.println(json.toString());
		handleServerResponse(in, stdIn, out); // 서버로부터의 응답 처리

		handleLoginSuccess(stdIn, out);
	}

	// 회원가입 기능을 처리하는 메서드
	private static void signup(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException {
		JSONObject json = new JSONObject();
		json.put("type", "signup");
		json.put("userid", promptForInput(stdIn, "사용할 아이디를 입력하세요"));
		json.put("password", promptForInput(stdIn, "사용할 패스워드를 입력하세요"));
		out.println(json.toString());
		handleServerResponse(in, stdIn, out); // 서버로부터의 응답 처리
	}

	// 사용자로부터 입력을 요청하는 메서드
	private static String promptForInput(BufferedReader stdIn, String prompt) throws IOException {
		System.out.println(prompt);
		return stdIn.readLine();
	}

	private static void handleServerResponse(BufferedReader in, BufferedReader stdIn, PrintWriter out)
			throws IOException {
		JSONParser parser = new JSONParser();
		try {
			 String responseString = in.readLine();
		        if (responseString != null) {
		            JSONObject response = (JSONObject) parser.parse(responseString);
		            System.out.println(response.toJSONString());
		        } else {
		            System.out.println("서버로부터 응답을 받지 못했습니다.");
		        }
		    } catch (ParseException e) {
		        System.out.println("서버로부터 응답을 파싱하는 중 오류가 발생했습니다: " + e.getMessage());
		    }
		}
	private static void handleLoginSuccess(BufferedReader stdIn, PrintWriter out) throws IOException {
		boolean keepRunning = true;
		while (keepRunning) {
			displayPostLoginMenu(); // 로그인 성공 후 메뉴 표시
			String userInput = stdIn.readLine(); // 사용자 입력 받기
			switch (userInput) {
			case "1":
				orderCoffee(stdIn, out); // 커피 주문 처리
				break;
			case "2":
				pointCharge(stdIn, out); // 채팅방 이동 처리
				break;
			case "3":
				enterChatRoom(stdIn, out); // 채팅방 이동 처리
				break;
			case "4":
				System.out.println("서비스를 종료합니다.");
				keepRunning = false; // 반복문 종료
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
				break;
			}
		}
	}
	
	
	private static void displayPostLoginMenu() {
		System.out.println("\n로그인에 성공했습니다.");
		System.out.println("1. 커피 주문");
		System.out.println("2. 포인트 충전");
		System.out.println("3. 채팅방 이동");
		System.out.println("4. 종료");
		System.out.println("메뉴를 선택하세요. >> ");

	}

	private static  void orderCoffee(BufferedReader stdIn, PrintWriter out) throws IOException {
		CoffeeOrder coffeeOrder = new CoffeeOrder();
		List<CoffeeOrder.CoffeeMenu> coffeeMenuList = coffeeOrder.getCoffeeMenuFromDatabase();
		
		
		//가져온 커피 메뉴를 출력하거나 사용자에게 보여줍니다.
		out.println("주문할 커피 메뉴:");
		for (int i = 0; i < coffeeMenuList.size(); i++) {
			 CoffeeOrder.CoffeeMenu coffeeMenu = coffeeMenuList.get(i);
			System.println((i+1)+","+ coffeeMenu.getName() + " - " + coffeeMenu.getPrice()+"원");
		}
		
		//사용자롭터 주문할 메뉴를 입력 받습니다. 
		//예시로 숫자로 메뉴를 선택하도록 구현했습니다.
		out.print("주문할 커피 메뉴 번호를 입력하세요: ");
		out.flush();
		int menuNumber = Integer.parseInt(stdIn.readLine());
		
		//선택된 메뉴를 주문합니다.
		if(menuNumber >= 1 && menuNumber <= coffeeMenuList.size()) {
			CoffeeOrder.CoffeeMenu selectedCoffee = coffeeMenuList.get(menuNumber-1);
			out.println(selectedCoffee.getName()+"를 주문하셨습니다.");
			//주문 처리 등의 작업을 이어서 수행할 수 있습니다.
		}else {
			out.println("잘못된 메뉴 번호입니다.");
		}
	}
	private static void pointCharge(BufferedReader stdIn, PrintWriter out) {
		// TODO Auto-generated method stub
		
	}


	private static void enterChatRoom(BufferedReader stdIn, PrintWriter out) {
		// TODO Auto-generated method stub
		
	}
	

	
	}

	

	