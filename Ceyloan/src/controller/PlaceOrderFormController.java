package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.KitchenItems;
import tm.AfterKitchenCartTm;
import tm.KitchenItemsTm;
import tm.KitchenPlaceOrder;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;



public class PlaceOrderFormController {


    public AnchorPane placeOrderAnchorPane;
    public TableView PlaceOrderTbl;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colRemove;
    private JFXPanel closeButton;

    public void initialize()  {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("cha"));
        try {
            getCart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PlaceOrderTbl.getColumns().setAll(colItemCode,colItemName,colPrice,colRemove);

    }


    ObservableList<KitchenPlaceOrder> placeCArt = new KitchenOrderController().returnKitchenCart();

    public void getCart() throws SQLException, ClassNotFoundException {

        PlaceOrderTbl .setItems(placeCArt);

    }


    public void checkOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/CheckOut.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void KitchenOrderOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/kitchenOrder.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void BarOrderOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/BarOrder.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) placeOrderAnchorPane.getScene().getWindow();

        stage.close();
    }




}

