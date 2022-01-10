package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.BarItems;
import model.Worker;
import tm.AfterBarCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckDeliveryBoy {
    public JFXTextField d1;
    public JFXTextField d2;
    public JFXTextField d3;
    public JFXTextField d4;
    public JFXTextField d5;
    public JFXTextField d6;


    /*public void initialize() {
        d1.setText(deliveryBoyList.get(0).getId());
        d2.setText(deliveryBoyList.get(1).getId());
        d3.setText(deliveryBoyList.get(2).getId());
        d4.setText(deliveryBoyList.get(3).getId());
        d5.setText(deliveryBoyList.get(4).getId());
        d6.setText(deliveryBoyList.get(5).getId());
    }
    static ObservableList<Worker> deliveryBoyList=FXCollections.observableArrayList();
    static ObservableList<String> deliveryBoyIdList=FXCollections.observableArrayList();*/

  /*  public   CheckDeliveryBoy() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM Worker WHERE type='delivery'");
        List< Worker> worker= FXCollections.observableArrayList();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            worker.add(new Worker(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
                    )
            );
        }
        for (Worker worker1:worker) {
            deliveryBoyList.add(new Worker(
                    worker1.getId(),
                    worker1.getName(),
                    worker1.getType(),
                    worker1.getStatus(),
                    worker1.getContact(),
                    worker1.getEmail()

            ));
        }
        for (Worker worker2  :worker) {
            deliveryBoyIdList.add(worker2.getId());
        }

    }
*/
    public void d2InCafeOnAction(ActionEvent event) {
        String id=d2.getText();
        try {
            setToInCafe(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void d1InCafeOnAction(ActionEvent event) {

        String id=d1.getText();
        try {
            setToInCafe(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void d3InCafeOnAction(ActionEvent event)  {

        String id=d3.getText();
        try {
            setToInCafe(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void d5InCafeOnAction(ActionEvent event) {

        String id=d5.getText();
        try {
            setToInCafe(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void d4InCafeOnAction(ActionEvent event) {

        String id=d4.getText();
        try {
            setToInCafe(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void d6InCafeOnAction(ActionEvent event) {

        String id=d6.getText();
        try {
            setToInCafe(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void setToInCafe(String id) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE  Worker SET status='INCAFE' WHERE id=?");
        stm.setObject(1,id );
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();
        }

    }

}
