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

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class BarController {
    public JFXTextField txtDesc;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtSize;
    public JFXTextField txtPrice;
    public JFXButton BarSaveOnAction;

    LinkedHashMap <TextField,Pattern> map = new LinkedHashMap();

    Pattern idPattern = Pattern.compile("^(B)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");       //^(B)[0-9][0-9][0-9]$
    Pattern salaryPattern = Pattern.compile("^[1-9]{1,10}$");


    public void initialize() {
        BarSaveOnAction.setDisable(true);
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

        Object response = ValidationUtill.validate(map,BarSaveOnAction);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }


    }



    public void BarItemCodeSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String bItemCode=txtItemCode.getText();
    BarItems barItems= new BarItemsController().getBarItems(bItemCode);
    if (barItems==null){
        new Alert(Alert.AlertType.WARNING,"Empty Result Text..").show();
    }else{
        setData(barItems);
    }
}

    private void setData(BarItems barItems) {
                                                                  txtItemCode.setText(barItems.getCode());
        txtItemName.setText(barItems.getName());
                                  txtDesc.setText(barItems.getDesc());txtSize.setText(barItems.getDesc());txtPrice.setText(String.valueOf(barItems.getPrice()));
    }


    public void BarSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

            BarItems barItems=new BarItems(
                    txtItemCode.getText(),
                    txtItemName.getText(),
                    txtDesc.getText(),
                    txtSize.getText(),
                    Integer.parseInt(txtPrice.getText()));
            if (new BarItemsController().saveBarItems(barItems)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            }
            else{
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }catch (Exception e){

        }
    }

    public void BarUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

            BarItems barItems=new BarItems(txtItemCode.getText(),
                    txtItemName.getText(),
                    txtDesc.getText(),
                    txtSize.getText(),
                    Integer.parseInt(txtPrice.getText())
            );

            if (new BarItemsController().updateBarItems(barItems)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated...").show();
                System.out.println("Ok");
            }else {
                new Alert(Alert.AlertType.WARNING, "Not Updated...").show();
                System.out.println("NoOk");
            }
        }catch (Exception e){

        }
    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

            String code=txtItemCode.getText();
            if (new BarItemsController().deleteBarItems(code)){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted..").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"NOT Deleted..").show();
            }
        }catch (Exception e){

        }



    }

    public static List<BarItems> getAllBarItems() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM BarItems");
        List<BarItems> barItems= FXCollections.observableArrayList();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            barItems.add(new BarItems(
                    rst.getString("code"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getString("size"),
                    rst.getInt("price")
            ));

        }


        return barItems;
    }



}
