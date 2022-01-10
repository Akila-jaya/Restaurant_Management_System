package tm;

public class    AfterKitchenCartTm {
    private String orderTime;
    private String itemName;
    private String size;
    private int orderQty;
    private String workerId;
    private String orderId;
    private String itemCode;

    public AfterKitchenCartTm(){

    }

    public AfterKitchenCartTm(String orderTime, String itemName, String size, int orderQty, String workerId, String orderId, String itemCode) {
        this.orderTime = orderTime;
        this.itemName = itemName;
        this.size = size;
        this.orderQty = orderQty;
        this.workerId = workerId;
        this.orderId = orderId;
        this.itemCode = itemCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
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
