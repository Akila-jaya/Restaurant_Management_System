package model;

public class Tables {
    private String id;
    private String name;
    private String orderId;
    private String status;
    private String noOfChairs;

    public Tables() {
    }

    public Tables(String id, String name, String orderId, String status, String noOfChairs) {
        this.id = id;
        this.name = name;
        this.orderId = orderId;
        this.status = status;
        this.noOfChairs = noOfChairs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNoOfChairs() {
        return noOfChairs;
    }

    public void setNoOfChairs(String noOfChairs) {
        this.noOfChairs = noOfChairs;
    }

    @Override
    public String toString() {
        return "Tables{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                ", noOfChairs='" + noOfChairs + '\'' +
                '}';
    }
}
