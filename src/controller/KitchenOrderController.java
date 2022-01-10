package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.KitchenItems;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.AfterKitchenCartTm;
import tm.KitchenItemsTm;
import tm.KitchenPlaceOrder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    //public JFXComboBox cmbDeliveryBoy;
    public JFXComboBox cmbWeighter;
    public Label lblTime;
    public Label lblDate;
    public Label lblKitchenOrderId;
    public JFXTextField txtSearch;

    public void initialize() throws SQLException, ClassNotFoundException {
        setOrderId();
       /*new SetWeighterController().setWeighter();*/
       /* ObservableList<String> oblist= FXCollections.observableArrayList(
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
        cmbOrderType.setItems(oblist);*/
       /* cmbDeliveryBoy.setItems(deliveryBoyIdList);
        cmbWeighter.setItems(weighterIdList);
*/

        getMenu();
        loadDateAndTime();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        col2OrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
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


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    search(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


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


   // int orderqty=1;


    static  ObservableList <KitchenPlaceOrder> kitchenCart=FXCollections.observableArrayList();
    static  ObservableList <AfterKitchenCartTm> cart=FXCollections.observableArrayList();
    public void getMenu() throws SQLException, ClassNotFoundException {
        List < KitchenItems > allCustomers= new KitchenController().getAllKitchenItems();
        ObservableList<KitchenItemsTm> tabledata=FXCollections.observableArrayList();
        ObservableList<KitchenPlaceOrder> placeCArt=FXCollections.observableArrayList();


        for (KitchenItems kitchenItems:allCustomers){

            Button btn=new Button("Add To Cart");
            tabledata.add(new KitchenItemsTm(
                    kitchenItems.getCode(),
                    kitchenItems.getName(),
                    kitchenItems.getDesc(),
                    kitchenItems.getSize(),
                    kitchenItems.getPrice(),
                    btn
            ));



            btn.setOnAction((e) -> {


                AfterKitchenCartTm afterCartTm=new AfterKitchenCartTm(
                        lblTime.getText(),
                        kitchenItems.getName(),
                        kitchenItems.getSize(),
                       1 ,
                        new PlaceOrderFormController().cmbDeliveryBoy(),
                        lblKitchenOrderId.getText(),
                        kitchenItems.getCode()
                );

                Button Pbtn=new Button("Remove");
                KitchenPlaceOrder kitchenPlaceOrder=new KitchenPlaceOrder(
                        kitchenItems.getCode(),
                        kitchenItems.getName(),
                        kitchenItems.getPrice(),
                       1,
                        Pbtn
                );

                int number=isExists(afterCartTm);
                if (number==-1){

                    cart.add(afterCartTm);
                    kitchenCart.add(kitchenPlaceOrder);


                    Pbtn.setOnAction((event) -> {
                        cart.remove(afterCartTm);
                        kitchenCart.remove(kitchenPlaceOrder);
                    });

                }else{


                    AfterKitchenCartTm temp=cart.get(number);
                    int x=temp.getOrderQty();


                    AfterKitchenCartTm newTm= null;

                        newTm = new AfterKitchenCartTm(
                                lblTime.getText(),
                                temp.getItemName(),
                                temp.getSize(),
                                ++x ,
                                new PlaceOrderFormController().cmbDeliveryBoy(),
                                lblKitchenOrderId.getText(),
                                temp.getItemCode()

                        );

                    cart.remove(number);
                    cart.add(newTm);


                    KitchenPlaceOrder kitchenPlaceOrder1=new KitchenPlaceOrder(
                            kitchenItems.getCode(),
                            kitchenItems.getName(),
                            kitchenItems.getPrice(),
                            x,
                            Pbtn
                    );

                    kitchenCart.remove(number);
                    kitchenCart.add(kitchenPlaceOrder1);
                    Pbtn.setOnAction((event) -> {
                        kitchenCart.remove(kitchenPlaceOrder1);
                        cart.remove(afterCartTm);
                    });
                }
                tblAfterOrder.setItems(cart);


            });
            KitchenItemTabl .setItems(tabledata);
        }


    }

    public ObservableList <KitchenPlaceOrder> returnKitchenCart(){
        return kitchenCart;
    }


    private int isExists(AfterKitchenCartTm afterCartTm){
        for (int i = 0; i < cart.size(); i++) {
            if (afterCartTm.getItemCode().equals(cart.get(i).getItemCode())){
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




    public void PrintToKitchenOnAction(ActionEvent event) {

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Kitchen_Order.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Get all values from table*/
            ObservableList<AfterKitchenCartTm> items = tblAfterOrder.getItems();
            /*Create a Bean Array Data Source and pass the table values to it*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,new JRBeanArrayDataSource(items.toArray()));

            JasperViewer.viewReport(jasperPrint, false);

            //if you wanna print the report directly you can use this instead of JasperViewer
            /*JasperPrintManager.printReport(jasperPrint,false);
*/
        } catch (JRException e) {
            e.printStackTrace();
        }

    }
    private void setOrderId() {
        try {

            lblKitchenOrderId.setText(new OrderController().getOrderId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtOnAction(ActionEvent event) {

       /* try {
            search(txtSearch.getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

    }

    private void search(String text) throws SQLException, ClassNotFoundException {

        List < KitchenItems > allCustomers= new KitchenController().getAllKitchenItems();


            List<KitchenItemsTm> kitchenItems = searchCustomer(text);
            ObservableList<KitchenItemsTm> tableData = FXCollections.observableArrayList();
            for (KitchenItemsTm kitchenItems1 : kitchenItems) {
                Button btn = new Button("Add To Cart");
                tableData.add(new KitchenItemsTm(
                        kitchenItems1.getCode(),
                        kitchenItems1.getName(),
                        kitchenItems1.getCode(),
                        kitchenItems1.getSize(),
                        kitchenItems1.getPrice(),
                        btn


                ));


                //===================





                    btn.setOnAction((e) -> {


                        AfterKitchenCartTm afterCartTm = new AfterKitchenCartTm(
                                lblTime.getText(),
                                kitchenItems1.getName(),
                                kitchenItems1.getSize(),
                                1,
                                new PlaceOrderFormController().cmbDeliveryBoy(),
                                lblKitchenOrderId.getText(),
                                kitchenItems1.getCode()
                        );

                        Button Pbtn = new Button("Remove");
                        KitchenPlaceOrder kitchenPlaceOrder = new KitchenPlaceOrder(
                                kitchenItems1.getCode(),
                                kitchenItems1.getName(),
                                kitchenItems1.getPrice(),
                                1,
                                Pbtn
                        );

                        int number = isExists(afterCartTm);
                        if (number == -1) {

                            cart.add(afterCartTm);
                            kitchenCart.add(kitchenPlaceOrder);


                            Pbtn.setOnAction((event) -> {
                                cart.remove(afterCartTm);
                                kitchenCart.remove(kitchenPlaceOrder);
                            });

                        } else {


                            AfterKitchenCartTm temp = cart.get(number);
                            int x = temp.getOrderQty();


                            AfterKitchenCartTm newTm = null;

                            newTm = new AfterKitchenCartTm(
                                    lblTime.getText(),
                                    temp.getItemName(),
                                    temp.getSize(),
                                    ++x,
                                    new PlaceOrderFormController().cmbDeliveryBoy(),
                                    lblKitchenOrderId.getText(),
                                    temp.getItemCode()

                            );

                            cart.remove(number);
                            cart.add(newTm);


                            KitchenPlaceOrder kitchenPlaceOrder1 = new KitchenPlaceOrder(
                                    kitchenItems1.getCode(),
                                    kitchenItems1.getName(),
                                    kitchenItems1.getPrice(),
                                    x,
                                    Pbtn
                            );

                            kitchenCart.remove(number);
                            kitchenCart.add(kitchenPlaceOrder1);
                            Pbtn.setOnAction((event) -> {
                                kitchenCart.remove(kitchenPlaceOrder1);
                                cart.remove(afterCartTm);
                            });
                        }
                        tblAfterOrder.setItems(cart);


                    });

                    //=================


                }
                // Set Data to  table
                KitchenItemTabl.getItems().setAll(tableData);

                // Set OnAction to table button


        }




    public static List<KitchenItemsTm> searchCustomer(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM KitchenItems WHERE name  LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<KitchenItemsTm> kitchenItems = new ArrayList<>();

        Button btn=new Button("Add To Cart");

        while (rs.next()) {
            kitchenItems.add(new KitchenItemsTm(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    btn

            ));
        }

        return kitchenItems;
    }


}

