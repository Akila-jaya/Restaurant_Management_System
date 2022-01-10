package tm;

import javafx.scene.control.Button;

public class CheckOutKitchenItems extends KitchenPlaceOrder {

    private String product;
    private int qty;
    private int price;
    private int total;
    private Button remove;

    public CheckOutKitchenItems() {
    }

    public CheckOutKitchenItems(String product, int qty, int price, int total, Button remove) {
        this.product = product;
        this.qty = qty;
        this.price = price;
        this.total = total;
        this.remove = remove;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }
}
