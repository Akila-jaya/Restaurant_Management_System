package tm;

import java.security.SecureRandom;

public class AfterBarCartTm {
    private  String time;
    private String name;
    private String size;
    private int orderqty;
    private String workerId;
    private String orderId;
    private String itemCode;

    public AfterBarCartTm() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AfterBarCartTm(String time,String name, String size, int orderqty, String workerId, String orderId, String itemCode) {
        this.time=time;
        this.name = name;
        this.size = size;
        this.orderqty = orderqty;
        this.workerId = workerId;
        this.orderId = orderId;
        this.itemCode = itemCode;
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

    public int getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(int orderqty) {
        this.orderqty = orderqty;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}




