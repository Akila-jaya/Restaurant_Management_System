package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.BarItems;
import model.KitchenItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class KitchenController {


    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtDesc;
    public JFXTextField txtSize;
    public JFXTextField txtPrice;

    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        KitchenItems kitchenItems=new KitchenItems(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtDesc.getText(),
                txtSize.getText(),
                txtPrice.getText()

        );
            if (new KitchenItemController().saveKitchenItems(kitchenItems)){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Not Saved").show();
            }

    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=txtItemCode.getText();
        if (new KitchenItemController().deleteKitchenItems(code)){
            new Alert(Alert.AlertType.CONFIRMATION,"DELETED..").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"TRY AGAIN..").show();
        }

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        KitchenItems kitchenItems=new KitchenItems(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtDesc.getText(),
                txtSize.getText(),
                txtPrice.getText()
        );

        if (new KitchenItemController().updateKitchenItems(kitchenItems)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"NO..").show();
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
        txtPrice.setText(kitchenItems.getPrice());
    }

    public void RefreshOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtItemName.clear();
        txtDesc.clear();
        txtSize.clear();
        txtPrice.clear();
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
                    rst.getString("price")
            ));

        }
        return kitchenItems;
    }


}
