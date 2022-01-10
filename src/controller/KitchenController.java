package controller;

import Utill.ValidationUtill;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.BarItems;
import model.KitchenItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class KitchenController {


    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtDesc;
    public JFXTextField txtSize;
    public JFXTextField txtPrice;
    public JFXButton SaveOnAction;


    //   Validation=======================

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern idPattern = Pattern.compile("^(K)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");       //^(B)[0-9][0-9][0-9]$
    Pattern salaryPattern = Pattern.compile("^[1-9]{1,10}$");


    public void initialize() {
        storeValidations();
    }
    private void storeValidations() {
        map.put(txtItemCode, idPattern);
        map.put(txtItemName, namePattern);
        map.put(txtDesc, namePattern);
        map.put(txtSize, namePattern);
        map.put(txtPrice, salaryPattern);
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


    //   Validation=======================







    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{

            KitchenItems kitchenItems=new KitchenItems(
                    txtItemCode.getText(),
                    txtItemName.getText(),
                    txtDesc.getText(),
                    txtSize.getText(),
                    Integer.parseInt(txtPrice.getText())

            );
            if (new KitchenItemController().saveKitchenItems(kitchenItems)){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Not Saved").show();
            }
        }catch (Exception e){

        }




    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

            String code=txtItemCode.getText();
            if (new KitchenItemController().deleteKitchenItems(code)){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"TRY AGAIN..").show();
            }
        }catch (Exception e){

        }



    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{

            KitchenItems kitchenItems = new KitchenItems(
                    txtItemCode.getText(),
                    txtItemName.getText(),
                    txtDesc.getText(),
                    txtSize.getText(),
                    Integer.parseInt(txtPrice.getText())
            );


            if (new KitchenItemController().updateKitchenItems(kitchenItems)){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"NO..").show();
            }
        }catch (Exception e){


        }


    }

    public void ItemCodeSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=txtItemCode.getText();
        KitchenItems kitchenItems=new KitchenItemController().getKitchenItems(code);

        if (kitchenItems==null){
            new Alert(Alert.AlertType.WARNING,"EMPTY SET..");

        }else {
                setData(kitchenItems);
            new Alert(Alert.AlertType.CONFIRMATION,"SEARCHED..");
        }

    }

    private void setData(KitchenItems kitchenItems) {
        txtItemCode.setText(kitchenItems.getCode());
        txtItemName.setText(kitchenItems.getName());
        txtDesc.setText(kitchenItems.getDesc());
        txtSize.setText(kitchenItems.getSize());
        txtPrice.setText(String.valueOf(kitchenItems.getPrice()));
    }

    public void RefreshOnAction(ActionEvent actionEvent) {

        try{

            txtItemCode.clear();
            txtItemName.clear();
            txtDesc.clear();
            txtSize.clear();
            txtPrice.clear();
        }catch (Exception e){

        }
    }

    public static List<KitchenItems> getAllKitchenItems() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM KitchenItems");
        List<KitchenItems> kitchenItems = FXCollections.observableArrayList();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            kitchenItems.add(new KitchenItems(
                    rst.getString("code"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getString("size"),
                    rst.getInt( "price")
            ));

        }
        return kitchenItems;
    }
}
