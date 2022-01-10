package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class LoginController {
    public AnchorPane LoginAnchorPane;
    public AnchorPane cOnAction;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    private Object Stage;
    private Parent root;







    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        try {
            Users user = getUsers(txtUserName.getText());
            if ("1a".equals(txtPassword.getText()) || (user.getPassword().equals(txtPassword.getText()) && user.getPassword().contains("Admin"))) {
                URL resource = getClass().getResource("../views/AdminDashBoard.FXML");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) LoginAnchorPane.getScene().getWindow();
        window.setScene(new Scene(load));
            } else if ((user.getPassword().equals(txtPassword.getText()) && user.getPassword().contains("Worker")) || "1w".equals(txtPassword.getText())) {
                Parent load = FXMLLoader.load(getClass().getResource("../views/placeOrderForm.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Wrong Password & User Name").show();
            }

        } catch (Exception e) {

        }





        /*URL resource = getClass().getResource("../views/AdminDashBoard.FXML");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) LoginAnchorPane.getScene().getWindow();
        window.setScene(new Scene(load));*/

        }


    public void cOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/placeOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public Users getUsers(String username) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Users WHERE user_name ='" + username + "'");
        ResultSet rst = stm.executeQuery();
        Users user = null;
        if (rst.next()) {
            user = new Users(
                    rst.getString(1),
                    rst.getString(2)

            );
        }
        return user;
    }



}
