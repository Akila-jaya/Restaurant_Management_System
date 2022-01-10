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
import java.sql.SQLException;
import java.util.List;

public class KitchenOrderController {
    public AnchorPane KitchenOrderAnchorPane;
    public JFXComboBox cmbOrderType;
    public JFXButton btnCheckTable;
    public JFXButton btnDeliveryBoy;
    public JFXButton btnCustomerInfo;
    public JFXButton btnSetWeighter;
    public TableView KitchenItemTabl;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colSize;
    public TableColumn colPrice;
    public TableColumn colAddToCart;
    public TableView tblAfterOrder;
    public TableColumn col2OrderTime;
    public TableColumn col2itemName;
    public TableColumn col2ItemSize;
    public TableColumn col2OrderQty;
    public TableColumn col2WorkerId;
    public TableColumn col2OrderId;
    public TableColumn col2ItemCode;

    public void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<String> oblist= FXCollections.observableArrayList(
                "Delivery","Take_Away","Dine_In"
        );
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
        cmbOrderType.setItems(oblist);

        getMenu();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAddToCart.setCellValueFactory(new PropertyValueFactory<>("btn"));

        KitchenItemTabl.getColumns().setAll(colItemCode,colItemName,colSize,colPrice,colAddToCart);

        col2OrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        col2itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        col2ItemSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        col2OrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        col2WorkerId.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        col2OrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        col2ItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));



        tblAfterOrder.getColumns().setAll(col2itemName,
                col2OrderTime,
                col2ItemSize,
                col2OrderQty,
                col2WorkerId,
                col2OrderId,
                col2ItemCode
        );
    }

    int number;
    int orderqty=1;



    static  ObservableList <AfterKitchenCartTm> cart=FXCollections.observableArrayList();
      static  ObservableList <KitchenPlaceOrder> kitchenCart=FXCollections.observableArrayList();

    public void getMenu() throws SQLException, ClassNotFoundException{

        List < KitchenItems > allCustomers= new KitchenController().getAllKitchenItems();
        ObservableList<KitchenItemsTm> tabledata=FXCollections.observableArrayList();
        ObservableList<KitchenPlaceOrder> placeCArt=FXCollections.observableArrayList();


        for (KitchenItems kitchenItems:allCustomers){
            //Button Pbtn = new Button("Remove");
            Button btn=new Button("Add To Cart");
            tabledata.add(new KitchenItemsTm(
                    kitchenItems.getCode(),
                    kitchenItems.getName(),
                    kitchenItems.getDesc(),
                    kitchenItems.getSize(),
                    kitchenItems.getPrice(),
                    btn
            ));

          /*  for (KitchenItems kitchenItems1:allCustomers) {
               // Button Pbtn = new Button("Remove");

                kitchenCart.add(new KitchenPlaceOrder(
                       kitchenItems1.getCode(),
                       kitchenItems1.getName(),
                       kitchenItems1.getPrice(),
                        kitchenItems1.getCha()
                ));
            }*/
            btn.setOnAction((e) -> {
             /*   Button Pbtn=new Button("Remove");*/
                AfterKitchenCartTm afterCartTm=new AfterKitchenCartTm("orderTime",kitchenItems.getName(),kitchenItems.getSize(),
                        orderqty,
                        "workerId",
                        "Order Id",
                        kitchenItems.getCode()
                );
                number=isExists(afterCartTm);
                if (number==-1){
                    cart.add(new AfterKitchenCartTm("orderTime",kitchenItems.getName(),kitchenItems.getSize(),
                            orderqty,
                            "workerId",
                            "Order Id",
                            kitchenItems.getCode()
                            ));
                    KitchenPlaceOrder kitchenPlaceOrder=new KitchenPlaceOrder(
                            kitchenItems.getCode(),
                            kitchenItems.getName(),
                            kitchenItems.getPrice(),
                        kitchenItems.getCode()
                    );
                    kitchenCart.add(kitchenPlaceOrder);
                }else{
                  /*  Button Pbtn=new Button("Remove");*/
                    orderqty=orderqty+1;
                   tblAfterOrder.getColumns();
                    cart.add(new AfterKitchenCartTm("orderTime",kitchenItems.getName(),kitchenItems.getSize(),
                            orderqty,
                            "workerId",
                            "Order Id",
                            kitchenItems.getCode()
                    ));
                    KitchenPlaceOrder kitchenPlaceOrder=new KitchenPlaceOrder(
                            kitchenItems.getCode(),
                            kitchenItems.getName(),
                            kitchenItems.getPrice(),
                            kitchenItems.getCode()
                    );
                    kitchenCart.add(kitchenPlaceOrder);
                }
                tblAfterOrder.setItems(cart);
            });
            KitchenItemTabl .setItems(tabledata);
        }
    }
    public  ObservableList <KitchenPlaceOrder>  returnKitchenCart(){

        return kitchenCart;
    }


    private int isExists(AfterKitchenCartTm afterCartTm){
        for (int i = 0; i < cart.size(); i++) {
            if (afterCartTm.getItemName().equals(cart.get(i).getItemName())){
                return i;
            }
        }
        return -1;
    }


    public void CheckTableOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/CheckTable.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(KitchenOrderAnchorPane).play();
    }

    public void DeliveryBoyOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/checkDeliveryBoy.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(KitchenOrderAnchorPane).play();

    }

    public void CustomerInfoOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/deliveryToCustomer.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(KitchenOrderAnchorPane).play();

    }

    public void MenuOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/KitchenMenu.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(KitchenOrderAnchorPane).play();

    }

    public void SetWeighterOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/SetWeighter.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(KitchenOrderAnchorPane).play();

    }

    public void ExitOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) KitchenOrderAnchorPane.getScene().getWindow();

        stage.close();
    }

}
