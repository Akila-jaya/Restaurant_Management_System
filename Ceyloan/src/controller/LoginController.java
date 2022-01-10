package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class LoginController {
    public AnchorPane LoginAnchorPane;
    public AnchorPane cOnAction;
    private Object Stage;
    private Parent root;







    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/AdminDashBoard.FXML");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) LoginAnchorPane.getScene().getWindow();
        window.setScene(new Scene(load));

    }


    public void cOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/placeOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
