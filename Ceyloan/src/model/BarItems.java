package model;

public class BarItems {
    private String code;
    private String name;
    private String desc;
    private String size;
    private int price;

    public BarItems(String code, String name, String desc, String size, int price) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.size = size;
        this.price = price;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    @Override
    public String toString() {
        return "BarItems{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
