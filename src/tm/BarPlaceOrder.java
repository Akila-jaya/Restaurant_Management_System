package tm;

import javafx.scene.control.Button;

public class BarPlaceOrder {
     private String barCode;
     private String barName;
     private int barPrice;
     private Button barPbtn;

    public BarPlaceOrder() {
    }

    public BarPlaceOrder(String code, String name, int price, Button pbtn) {
        this.barCode= code;
        this.barName = name;
        this.barPrice = price;
        barPbtn = pbtn;
    }

    public String getCode() {
        return barCode;
    }

    public void setCode(String code) {
        this.barCode = code;
    }

    public String getName() {
        return barName;
    }

    public void setName(String name) {
        this.barName = name;
    }

    public int getPrice() {
        return barPrice;
    }

    public void setPrice(int price) {
        this.barPrice= price;
    }

    public Button getPbtn() {
        return barPbtn;
    }

    public void setPbtn(Button pbtn) {
        barPbtn = pbtn;
    }


}
