package tm;

import javafx.scene.control.Button;

public class BookTablesTm {
    private String id;
    private String name;
    private String noOfChairs;
    private String status;
    private Button btn;


    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }


    public BookTablesTm() {
    }

    public BookTablesTm(String id, String name, String noOfChairs, String status,Button btn) {
        this.id = id;
        this.name = name;
        this.noOfChairs = noOfChairs;
        this.status = status;
        this.btn = btn;
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

    public String getNoOfChairs() {
        return noOfChairs;
    }

    public void setNoOfChairs(String noOfChairs) {
        this.noOfChairs = noOfChairs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
