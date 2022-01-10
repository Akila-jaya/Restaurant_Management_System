package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Worker;
import tm.SetDeliveryBoyTm;
import tm.SetWeighterTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SetWeighterController {


    public AnchorPane SetWeighterAnchor;
    public TableView tblViewWeighter;
    public TableColumn colWorkerName;
    public TableColumn colWorkerCode;
    public TableColumn colWorkerContact;
    public TableColumn colWorkerType;
    public TableColumn colStatus;
    public TableColumn colToInCafe;
    private Object SetWeighterTm;

    public void initialize(){

        try {
            GetOutCafeWeighter();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colWorkerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWorkerCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colWorkerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colWorkerType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colToInCafe.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }





    public   void GetOutCafeWeighter() throws SQLException, ClassNotFoundException {
        ObservableList<SetWeighterTm> weighterList= FXCollections.observableArrayList();

        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM Worker WHERE status='busy'");
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
            Button btn=new Button("To Free");
            weighterList.add(new SetWeighterTm(
                    worker1.getName(),
                    worker1.getId(),
                    worker1.getContact(),
                    worker1.getType(),
                    worker1.getStatus(),
                    btn
            ));

            btn.setOnAction((e) -> {
                try {
                    setToInCafe(worker1.getId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

                weighterList.remove(SetWeighterTm);
                try {
                    GetOutCafeWeighter();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }


            });
        }

        tblViewWeighter.setItems(weighterList);
    }



    public void setToInCafe(String id) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE  Worker SET status='free' WHERE id=?");
        stm.setObject(1,id );
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();
        }

    }


}
