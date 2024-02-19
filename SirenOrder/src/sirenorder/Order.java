package sirenorder;

public class Order {
    private String coffeeType;
    private String size;
    private boolean syrupAdded;
    private String userId;
    private String coffeeOption; // 커피 옵션: ice/hot
    private String consumptionMethod; // 취식 방법: 매장이용/테이크아웃

    public static int money = 10000; // 초기 잔액

    public Order(String coffeeType, String size, boolean syrupAdded, String userId, String coffeeOption, String consumptionMethod) {
        this.coffeeType = coffeeType;
        this.size = size;
        this.syrupAdded = syrupAdded;
        this.userId = userId;
        this.coffeeOption = coffeeOption;
        this.consumptionMethod = consumptionMethod;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public String getSize() {
        return size;
    }

    public boolean isSyrupAdded() {
        return syrupAdded;
    }

    public String getUserId() {
        return userId;
    }

    public String getCoffeeOption() {
        return coffeeOption;
    }

    public String getConsumptionMethod() {
        return consumptionMethod;
    }

    // 결제 메서드
    public boolean makePayment(int price) {
        if (money >= price) {
            money -= price;
            return true; // 결제 성공
        } else {
            return false; // 결제 실패
        }
    }

    // 주문 정보 문자열 반환
    public String toString() {
        return userId + "님 주문하신 커피 나왔습니다: " + size + " 사이즈 " + coffeeType 
            + (syrupAdded ? " (시럽 추가)" : "") + ", 옵션: " + coffeeOption 
            + ", 취식 방법: " + consumptionMethod + ", 결제 후 잔액: " + money + "원";
    }

    public static class Payment {
        private String userId;
        public static int money = 10000;

        public Payment(String userId, int money) {
            this.userId = userId;
            this.money = money;
        }

        public String getUserId() {
            return userId;
        }
        
        public int savedMoney() { // 추후에 잔액 확인 기능 구현 가능
            return money;
        }
        
        public static int leftMoney(int price) {
            money -= price;
            return money;
        }
    }
}
