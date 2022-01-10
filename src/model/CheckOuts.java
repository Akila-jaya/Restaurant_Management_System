package model;

public class CheckOuts {
    private String product;
    private int qty;
    private int price;
    private int total;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CheckOuts() {
    }

    public CheckOuts(String product, int qty, int price, int total, String date) {
        this.product = product;
        this.qty = qty;
        this.price = price;
        this.total = total;
        this.date = date;
    }

    private String date;


}