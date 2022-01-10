package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import tm.*;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static controller.AdminDashBoardController.lblDate;
import static controller.BarOrderController.PlaceBarCart;
import static controller.BarOrderController.cartBar;

import static controller.KitchenOrderController.cart;
import static controller.KitchenOrderController.kitchenCart;
//import static controller.SetWeighterController.weighterIdList;
//import static controller.SetWeighterController.setWeighter;

//import static controller.KitchenOrderController.kitchenCart;


public class PlaceOrderFormController {


    public AnchorPane placeOrderAnchorPane;
    public TableView PlaceOrderTbl;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colRemove;
    public Label lblOrderId;
    public JFXComboBox cmbOrderType;
    public JFXButton btnDeliveryBoy;
    public JFXButton btnCheckTable;
    public JFXButton btnCustomerInfo;
    public  JFXButton btnSetWeighter;
    public JFXButton btnSetWeightier;
    public JFXComboBox cmbWeighter;
    public JFXComboBox cmbDeliveryBoy;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbTable;
    public JFXComboBox cmbDeliveryCustomer;
    public JFXComboBox cmbOrderIds;
    private JFXPanel closeButton;
    static String newValue1;
    static String newValue2;
    static String newValue3;
    static String type;
    static String lbl;
    static String NewValue4;


     public void initialize() throws SQLException, ClassNotFoundException {
         setOrderId();
         loadDateAndTime();
         cmbgetDBoy();
         cmbgetWBoy();
         cmbgetTable();
         cmbgetDeliverCustomer();

        lbl=lblOrderId.getText();




        //=============delivey set combo
        cmbDeliveryBoy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            newValue1=String.valueOf(newValue);
            try {
                setToDelivery(newValue1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });



         cmbDeliveryCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
             NewValue4=String.valueOf(newValue);
             try {
                 setToDeliveryCustomerDelete(NewValue4);
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }
         });

         cmbWeighter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            newValue2=String.valueOf(newValue);
            try {
                setToBusy(newValue2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //cmbWeighter.refresh();
        });



         cmbTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

             newValue3=String.valueOf(newValue);
             try {
                 setToTableBusy(newValue3);
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }
         });





            //=============delivey set combo

        //==================new update
        ObservableList<String> oblist= FXCollections.observableArrayList(
                "Delivery","Take_Away","Dine_In"
        );
        cmbOrderType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

             type= String.valueOf(newValue);

            btnCheckTable.setDisable(false);
            btnCustomerInfo.setDisable(false);
            btnDeliveryBoy.setDisable(false);
            //btnSetWeighter.setDisable(false);

            if (type=="Delivery"){
                //btnSetWeighter.setDisable(true);
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


        //================== close update
        try {
            getCart();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("Pbtn"));

        PlaceOrderTbl.getColumns().setAll(colItemCode,colItemName,colPrice,colQty,colRemove);


       //=======================================new update
        cmbDeliveryBoy.setItems( statusDeliveryBoyIdList);
        cmbWeighter.setItems(statusWeighterIdList);
        cmbTable.setItems(cmbTablesIdlist);
        cmbDeliveryCustomer.setItems(DeliveryCustomerIds);
        //=======================================close update
    }

    public String returnOrderType(){

         return type;
    }



    public void setToDeliveryCustomerDelete(String id) throws SQLException, ClassNotFoundException{
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE Customer SET name='ordered' WHERE id=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();
        }
     }

     static ObservableList<Customer> DeliveryCustomer=FXCollections.observableArrayList();
    static ObservableList<String> DeliveryCustomerIds=FXCollections.observableArrayList();
    public  void cmbgetDeliverCustomer() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM Customer;");
        List<Customer> customers= FXCollections.observableArrayList();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            customers.add(new Customer(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getInt(5)

                    )
            );
        }
        for (Customer customer:customers) {
            DeliveryCustomer.add(new Customer(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact(),
                    customer.getNumberOfPpl()


            ));
        }
        for (Customer customer2  :customers) {
            DeliveryCustomerIds.add(customer2.getId());
        }
    }




    public void setToDelivery(String id) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE  Worker SET status='delivery' WHERE id=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();
        }


    }



    public void setToBusy(String id) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE  Worker SET status='busy' WHERE id=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();

        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();

        }

    }



    public void setToTableBusy(String id) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE  customerTable SET status='busy' WHERE id=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();
        }

    }





    static ObservableList<String> statusDeliveryBoyIdList=FXCollections.observableArrayList();
     static ObservableList<Worker> pdeliveryBoyList=FXCollections.observableArrayList();
    public  void cmbgetDBoy() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM Worker WHERE type='delivery' AND status='INCAFE'");
        List<Worker> worker= FXCollections.observableArrayList();
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
            pdeliveryBoyList.add(new Worker(
                    worker1.getId(),
                    worker1.getName(),
                    worker1.getType(),
                    worker1.getStatus(),
                    worker1.getContact(),
                    worker1.getEmail()

            ));
        }
        for (Worker worker2  :worker) {
            statusDeliveryBoyIdList.add(worker2.getId());
        }
        System.out.println();

    }


    //====================================================



    static ObservableList<String> statusWeighterIdList=FXCollections.observableArrayList();

    static ObservableList<Worker> pWeighterList=FXCollections.observableArrayList();
    public  void cmbgetWBoy() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM Worker WHERE type='weighter' AND status='free'");
        List<Worker> worker= FXCollections.observableArrayList();
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
            pWeighterList.add(new Worker(
                    worker1.getId(),
                    worker1.getName(),
                    worker1.getType(),
                    worker1.getStatus(),
                    worker1.getContact(),
                    worker1.getEmail()

            ));
        }
        for (Worker worker2  :worker) {
            statusWeighterIdList.add(worker2.getId());
        }
        System.out.println();

    }


    //=============================================
    static  ObservableList<String> cmbTablesIdlist=FXCollections.observableArrayList();
    public  void cmbgetTable() throws SQLException, ClassNotFoundException {
         ObservableList<Tables> cmbTableslist=FXCollections.observableArrayList();


        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM customerTable WHERE  status='free'");
        List <Tables> tables= FXCollections.observableArrayList();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            tables.add(new Tables(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5)
                    )
            );
        }
        for (Tables tables1:tables) {
            cmbTableslist.add(new Tables(
                    tables1.getId(),
                    tables1.getName(),
                    tables1.getOrderId(),
                    tables1.getStatus(),
                    tables1.getNoOfChairs()
            ));
        }
        for (Tables tables2:tables) {
            cmbTablesIdlist.add(tables2.getId());
        }


    }
    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }






    public String setLblOrderId(){


        return lbl;

    }


    public void  getCart() throws SQLException, ClassNotFoundException{
        PlaceOrderTbl.setItems(kitchenCart);
    }
    public  ObservableList <KitchenPlaceOrder> returnKitchenCart(){

        return  kitchenCart;
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
    //=============get cmbDeliveryBoy new value
    public String cmbDeliveryBoy() {

        if (newValue1==null){

            return newValue2;
        }else{

            return newValue1;
        }
    }


    private void setOrderId() {
        try {

            lblOrderId.setText(new OrderController().getOrderId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    public void PlaceOrderForm(ActionEvent event) {

        ArrayList<AfterKitchenCartTm> afterKitchenCartTm= new ArrayList<>();
        ArrayList<AfterBarCartTm> afterBarCartTm= new ArrayList<>();


        for (AfterKitchenCartTm afterKitchenCartTm1:cart
        ) {
            afterKitchenCartTm.add(new AfterKitchenCartTm(
                     lblTime.getText(),
                    afterKitchenCartTm1.getItemName(),
                    afterKitchenCartTm1.getSize(),
                    afterKitchenCartTm1.getOrderQty(),
                    afterKitchenCartTm1.getWorkerId(),
                    afterKitchenCartTm1.getOrderId(),
                    afterKitchenCartTm1.getItemCode()
            ));

        }
        for (AfterBarCartTm afterBarCartTm1:cartBar
        ) {
            afterBarCartTm.add(new AfterBarCartTm(
                   lblTime.getText(),
                    afterBarCartTm1.getName(),
                    afterBarCartTm1.getSize(),
                    afterBarCartTm1.getOrderqty(),
                    afterBarCartTm1.getWorkerId(),
                    afterBarCartTm1.getOrderId(),
                    afterBarCartTm1.getItemCode()
            ));
        }
         Order order= new Order(
               lblOrderId.getText(),
                 NewValue4,
                 type,
                  lblDate.getText(),
                 lblTime.getText(),
                 afterKitchenCartTm,
                 afterBarCartTm
         );

        KitchenOrderDetail kitchenOrderDetail=new KitchenOrderDetail(
                afterKitchenCartTm
        );
        BarOrderDetail barOrderDetail=new BarOrderDetail(
                afterBarCartTm
        );

        if (new OrderController().placeOrder(order,barOrderDetail,kitchenOrderDetail)){
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setOrderId();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }


    }

    public void CheckTableOnAction(ActionEvent event) throws IOException {

        Parent load = FXMLLoader.load(getClass().getResource("../views/CheckTable.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(placeOrderAnchorPane).play();
    }

    public void DeliveryBoyOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/newSetDeliveryBoy.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(placeOrderAnchorPane).play();
    }

    public void CustomerInfoOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/deliveryToCustomer.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(placeOrderAnchorPane).play();
    }

    public void SetWeighterOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/SetWeighter.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(placeOrderAnchorPane).play();

    }


}

