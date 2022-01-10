package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BarItems;
import tm.AfterBarCartTm;
import tm.AfterKitchenCartTm;
import tm.BarItemsTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BarOrderController{


    public AnchorPane BarOrderAnchorPane;
    public JFXComboBox cmbOrderType;
    public JFXButton btnSetWeighter;
    public JFXButton btnCheckTable;
    public JFXButton btnCustomerInfo;
    public JFXButton btnDeliveryBoy;
    public TableColumn colItemName;
    public TableColumn colItemSize;
    public TableColumn colItemPrice;
    public TableColumn colAddToCart;
    public TableColumn colItemCode;
    public TableColumn colItemQty;
    public TableView BarItemtbl;
    public TableColumn colItemAddToCart;
    public TableColumn colIDesc;
    public TableColumn colItemSDesc;
    public TableColumn collAddToCart;
    public TableView tblBarAddToCart;
    public TableColumn colOrderTime;
    public TableColumn col2OrderTime;
    public TableColumn colI2temName;
    public TableColumn colI2temSize;
    public TableColumn colOrderQty;
    public TableColumn colWorkerId;
    public TableColumn colOrderId;
    public TableColumn colI2temCode;

    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<String> oblist= FXCollections.observableArrayList(
                "Delivery","Take_Away","Dine_In"
        );
        cmbOrderType.setItems(oblist);

        cmbOrderType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            String type= String.valueOf(newValue);

            btnCheckTable.setDisable(false);
            btnCustomerInfo.setDisable(false);
            btnDeliveryBoy.setDisable(false);
            btnSetWeighter.setDisable(false);

            if (type=="Delivery"){
                btnSetWeighter.setDisable(true);
                btnCheckTable.setDisable(true);
            }else {

                if (type=="Take_Away"){
                    btnCheckTable.setDisable(true);
                    btnCustomerInfo.setDisable(true);
                    btnDeliveryBoy.setDisable(true);
                }else{



                    if (type=="Dine_In"){

                        btnDeliveryBoy.setDisable(true);
                    }
                }
            }


        });


        getMenu();
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        collAddToCart.setCellValueFactory(new PropertyValueFactory<>("btn"));

        BarItemtbl.getColumns().setAll(colItemCode,colItemName,colItemSize,colItemPrice,collAddToCart);

        colI2temName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderqty"));
        colI2temSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        //colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderqty"));
        colWorkerId.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colI2temCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        col2OrderTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        tblBarAddToCart.getColumns().setAll(colI2temName,colOrderQty,colI2temSize, colWorkerId,colOrderId,colI2temCode,col2OrderTime);
    }



    int number;
    int orderqty=1;

    public void CheckTableOnAction(ActionEvent actionEvent) throws IOException {

        Parent load = FXMLLoader.load(getClass().getResource("../views/CheckTable.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(BarOrderAnchorPane).play();
    }


    ObservableList <AfterBarCartTm> cartBar=FXCollections.observableArrayList();
    public void getMenu() throws SQLException, ClassNotFoundException {
        List <BarItems> allCustomers= new BarController().getAllBarItems();
        ObservableList<BarItemsTm> tabledata=FXCollections.observableArrayList();
        for (BarItems barItems:allCustomers) {
            Button btn=new Button("Add To CArt");
            tabledata.add(new BarItemsTm(
                    barItems.getCode(),
                    barItems.getName(),
                    barItems.getDesc(),
                    barItems.getSize(),
                    barItems.getPrice(),
                    btn
            ));
            btn.setOnAction((e) -> {
                AfterBarCartTm afterBarCartTm=new AfterBarCartTm("time",barItems.getName(),
                        barItems.getSize(),
                        orderqty,
                        "Akila",
                        "c001",
                         barItems.getCode()
                );
                number= isExists(afterBarCartTm);

               if (number==-1){
                   cartBar.add(new AfterBarCartTm("time",barItems.getName(),
                           barItems.getSize(),
                           orderqty,
                           "Akila",
                           "c001",
                           barItems.getCode()
                   ));
               }else {
                 orderqty=orderqty+1;
                 tblBarAddToCart.getColumns();
                   cartBar.add(new AfterBarCartTm("time",barItems.getName(),
                           barItems.getSize(),
                           orderqty,
                           "Akila",
                           "c001",
                           barItems.getCode()
                   ));
               }
               tblBarAddToCart.setItems(cartBar);
            });
        }
        BarItemtbl.setItems(tabledata);
    }



    private int isExists(AfterBarCartTm afterBarCartTm){
        for (int i = 0; i < cartBar.size(); i++) {
            if (afterBarCartTm.getItemCode()
                    .equals(cartBar.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }
    public void DeliveryBoyOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/checkDeliveryBoy.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(BarOrderAnchorPane).play();
    }
    public void CustomerInfoOnAction(ActionEvent actionEvent) throws IOException{
        Parent load = FXMLLoader.load(getClass().getResource("../views/deliveryToCustomer.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(BarOrderAnchorPane).play();

    }

    public void MenuOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/BarMenu.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(BarOrderAnchorPane).play();

    }

    public void SetWeighterOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/SetWeighter.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(BarOrderAnchorPane).play();

    }

    public void ExitOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) BarOrderAnchorPane.getScene().getWindow();

        stage.close();
    }
}
