package controller;

import Utill.ValidationUtill;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Tables;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class TableController {

    public JFXTextField txtTableId;
    public JFXTextField txtTableName;
    public JFXTextField txtNumberOfChairs;
    public JFXTextField txtOrderId;
    public JFXTextField txtStatus;
    public JFXButton SaveOnAction;

    //========Validation=============


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern idPattern = Pattern.compile("^(T)[0-9][-][0-9]{1,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{1,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");       //^(B)[0-9][0-9][0-9]$
    Pattern salaryPattern = Pattern.compile("^[1-9]{0,4}$");


    public void initialize() {
        storeValidations();
    }
    private void storeValidations() {
        map.put(txtTableId, idPattern);
        map.put(txtTableName, namePattern);
        map.put(txtNumberOfChairs, salaryPattern);
        map.put(txtOrderId, salaryPattern);
        map.put(txtStatus, namePattern);
    }



    public void textFields_Key_Released(KeyEvent keyEvent) {

        Object response = ValidationUtill.validate(map,SaveOnAction);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }


    }


    //========Validation=============







    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

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
        }catch (Exception e){

        }

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{
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

        }catch (Exception e){

        }

    }

    public void RefreshOnAction(ActionEvent actionEvent) {

    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

            String code=txtTableId.getText();
            if (new TableInterfaceController().tableDelete(code)){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"NOT DELETED..").show();
            }
        }catch (Exception e){

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
