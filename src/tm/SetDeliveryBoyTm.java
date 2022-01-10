package tm;

import javafx.scene.control.Button;

public class SetDeliveryBoyTm {

    private String name;
    private String id;
    private String contact;
    private String type;
    private String status;
    private Button btn;

    public SetDeliveryBoyTm() {
    }

    public SetDeliveryBoyTm(String name, String id, String contact, String type, String status, Button btn) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.type = type;
        this.status = status;
        this.btn = btn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}

