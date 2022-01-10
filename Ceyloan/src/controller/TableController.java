package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Tables;

import java.sql.SQLException;

public class TableController {

    public JFXTextField txtTableId;
    public JFXTextField txtTableName;
    public JFXTextField txtNumberOfChairs;
    public JFXTextField txtOrderId;
    public JFXTextField txtStatus;

    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Tables tables=new Tables(
                txtTableId.getText(),
                txtTableName.getText(),
                 txtOrderId.getText(),
               txtStatus.getText(),
               txtNumberOfChairs.getText()
        );
        if (new TableInterfaceController().tableSave(tables)){
            new Alert(Alert.AlertType.CONFIRMATION,"SAVED..").show();

        }else{
            new Alert(Alert.AlertType.WARNING,"TRY AGAIN..").show();
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Tables tables=new Tables(
                txtTableId.getText(),
                txtTableName.getText(),
                txtOrderId.getText(),
                txtStatus.getText(),
                txtNumberOfChairs.getText()
        );
       if( new TableInterfaceController().tableUpdate(tables)){
           new Alert(Alert.AlertType.CONFIRMATION,"UPDATED..").show();
       }else{
           new Alert(Alert.AlertType.WARNING,"TRY AGAIN..").show();
       }

    }

    public void RefreshOnAction(ActionEvent actionEvent) {

    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=txtTableId.getText();
        if (new TableInterfaceController().tableDelete(code)){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED..").show();
        }else{
                new Alert(Alert.AlertType.WARNING,"NOT DELETED..").show();
        }

    }

    public void TableIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=txtTableId.getText();
         Tables tables=new TableInterfaceController().getTables(id);
         if (tables==null){
             new Alert(Alert.AlertType.WARNING,"Emty Result..");
         }else{
             setData(tables);
         }



    }

    private void setData(Tables tables) {
        txtTableId.setText(tables.getId());
        txtTableName.setText(tables.getName());
        txtOrderId.setText(tables.getOrderId());
        txtStatus.setText(tables.getStatus());
        txtNumberOfChairs.setText(tables.getNoOfChairs());
    }
}
