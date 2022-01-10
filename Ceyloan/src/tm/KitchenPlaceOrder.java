package tm;


import javafx.scene.control.Button;

public class KitchenPlaceOrder {
    private String code;
    private String name;
    private String price;
    private String cha;

    public KitchenPlaceOrder() {
    }

    public KitchenPlaceOrder(String code, String name, String price, String cha) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.cha = cha;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCha() {
        return cha;
    }

    public void setCha(String cha) {
        this.cha = cha;
    }
}


