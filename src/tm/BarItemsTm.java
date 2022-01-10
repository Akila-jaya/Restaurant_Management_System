package tm;

import javafx.scene.control.Button;

public class BarItemsTm {
        private String code;
        private String name;
       /* private String description;*/
        private String size;
        private int price;
         private Button btn;


    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public BarItemsTm(String code, String name, String size, int price, Button btn) {
    }

    public BarItemsTm(String code, String name, String description, String size, int price,Button btn) {
        this.code = code;
        this.name = name;
     /*   this.description = description;*/
        this.size = size;
        this.price = price;
        this.setBtn(btn);
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

   /* public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/

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
}
