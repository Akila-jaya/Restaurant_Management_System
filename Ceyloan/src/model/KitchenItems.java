package model;

public class KitchenItems{
private String code;
private String name;
private String desc;
private String size;
private String price;

    public KitchenItems() {
    }

    public KitchenItems(String code, String name, String desc, String size, String price) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.size = size;
        this.price = price;
    }

    public  String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public  String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public  String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "KitchenItems{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
