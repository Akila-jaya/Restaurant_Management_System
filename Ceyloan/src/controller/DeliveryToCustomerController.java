package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.DeliverToCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryToCustomerController {


    public JFXTextField txtcustName;
    public JFXTextField txtcustContactNumber;
    public JFXTextField txtCustId;
    public JFXTextField txtCustAddress;
    public JFXTextField txtNumberOfPeople;


    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

                String id=txtCustId.getText();
                String name=txtcustName.getText();
               String address= txtCustAddress.getText();
                String number=txtcustContactNumber.getText();
                Integer numberOfPeople= Integer.valueOf(txtNumberOfPeople.getText());

        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO   Customer VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,id);
        stm.setObject(2,name);
        stm.setObject(3,address);
        stm.setObject(4,number);
        stm.setObject(5,numberOfPeople);
        if (stm.executeUpdate()>0){
            new Alert(Alert.AlertType.CONFIRMATION,"SAVE..").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NOT SAVED..").show();
        }

    }
}
