package tm;


import javafx.scene.control.Button;

public class KitchenPlaceOrder {
    private String code;
    private String name;
    private int price;
    private int orderQty;
    private Button Pbtn;


    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public KitchenPlaceOrder() {
    }

    public KitchenPlaceOrder(String code, String name, int price, int orderQty, Button pbtn) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.orderQty = orderQty;
        Pbtn = pbtn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Button getPbtn() {
        return Pbtn;
    }

    public void setPbtn(Button pbtn) {
        Pbtn = pbtn;
    }
}

