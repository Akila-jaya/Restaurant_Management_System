package tm;

import javafx.scene.control.Button;

public class KitchenItemsTm {

     private String code;
     private String name;
    private String desc;
    private String size;
    private int price;
    private Button btn;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }





    public KitchenItemsTm(String code, String name, String desc, String size, int price, Button btn) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.size = size;
        this.price = price;
        this.btn = btn;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }


    @Override
    public String toString() {
        return "KitchenItemsTm{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", btn=" + btn +
                '}';
    }
}
