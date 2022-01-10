package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.BarItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BarController {

    public JFXTextField txtDesc;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtSize;
    public JFXTextField txtPrice;

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
        txtDesc.setText(barItems.getDesc());
        txtSize.setText(barItems.getDesc());
        txtPrice.setText(String.valueOf(barItems.getPrice()));
    }


    public void BarSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
    }

    public void BarUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
                    BarItems barItems=new BarItems( txtItemCode.getText(),
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
}

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=txtItemCode.getText();
            if (new BarItemsController().deleteBarItems(code)){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted..").show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"NOT Deleted..").show();
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
