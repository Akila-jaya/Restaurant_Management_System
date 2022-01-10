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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BarItems;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.KitchenOrderController.kitchenCart;

public class BarOrderController {


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
    public Label lblDate;
    public Label lblTime;
    public Label lblBarOrderId;
    public JFXTextField txtBarSearch;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        setOrderId();

        getMenu();
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        collAddToCart.setCellValueFactory(new PropertyValueFactory<>("btn"));

        BarItemtbl.getColumns().setAll(colItemCode, colItemName, colItemSize, colItemPrice, collAddToCart);

        colI2temName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderqty"));
        colI2temSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        //colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderqty"));
        colWorkerId.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colI2temCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        col2OrderTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        tblBarAddToCart.getColumns().setAll(colI2temName, colOrderQty, colI2temSize, colWorkerId, colOrderId, colI2temCode, col2OrderTime);


        txtBarSearch.textProperty().addListener(new ChangeListener<String>() {
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


    int orderqty = 1;

    public void CheckTableOnAction(ActionEvent actionEvent) throws IOException {

        Parent load = FXMLLoader.load(getClass().getResource("../views/CheckTable.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new FadeIn(BarOrderAnchorPane).play();

    }


    static ObservableList<AfterBarCartTm> cartBar = FXCollections.observableArrayList();
    static ObservableList<KitchenPlaceOrder> PlaceBarCart = FXCollections.observableArrayList();

    public void getMenu() throws SQLException, ClassNotFoundException {
        List<BarItems> allCustomers = new BarController().getAllBarItems();
        ObservableList<BarItemsTm> tabledata = FXCollections.observableArrayList();


        for (BarItems barItems : allCustomers) {
            Button btn = new Button("Add To CArt");
            tabledata.add(new BarItemsTm(
                    barItems.getCode(),
                    barItems.getName(),
                    barItems.getDesc(),
                    barItems.getSize(),
                    barItems.getPrice(),
                    btn
            ));
            btn.setOnAction((e) -> {
                AfterBarCartTm afterBarCartTm = new AfterBarCartTm(lblTime.getText(), barItems.getName(),
                        barItems.getSize(),
                        1,
                        new PlaceOrderFormController().cmbDeliveryBoy(),
                        new PlaceOrderFormController().setLblOrderId(),
                        barItems.getCode()
                );
                Button Pbtn = new Button("Remove");
                KitchenPlaceOrder kitchenPlaceOrder = new KitchenPlaceOrder(
                        barItems.getCode(),
                        barItems.getName(),
                        barItems.getPrice(),
                        orderqty,
                        Pbtn
                );
                int number = isExists(afterBarCartTm);

                if (number == -1) {
                  /* cartBar.add(new AfterBarCartTm("time",barItems.getName(),
                           barItems.getSize(),
                           orderqty,
                           new PlaceOrderFormController().cmbDeliveryBoy(),
                           "c001",
                           barItems.getCode()
                   ));*/


                 /*  KitchenPlaceOrder kitchenPlaceOrder=new KitchenPlaceOrder(
                           barItems.getCode(),
                           barItems.getName(),
                           barItems.getPrice(),
                           orderqty,
                           Pbtn
                   );*/
                    cartBar.add(afterBarCartTm);
                    kitchenCart.add(kitchenPlaceOrder);

                    Pbtn.setOnAction((event) -> {
                        cartBar.remove(afterBarCartTm);
                        kitchenCart.remove(kitchenPlaceOrder);
                    });


                } else {
                    AfterBarCartTm temp = cartBar.get(number);
                    int x = temp.getOrderqty();
                   /*cartBar.add(new AfterBarCartTm("time",barItems.getName(),
                           barItems.getSize(),
                           orderqty,
                           new PlaceOrderFormController().cmbDeliveryBoy(),
                           "c001",
                           barItems.getCode()
                   ));*/

                    AfterBarCartTm newTm = new AfterBarCartTm(
                            lblTime.getText(),
                            temp.getName(),
                            temp.getSize(),
                            ++x,
                            new PlaceOrderFormController().cmbDeliveryBoy(),
                            new PlaceOrderFormController().setLblOrderId(),
                            temp.getItemCode()

                    );
                    cartBar.remove(number);
                    cartBar.add(newTm);


                    KitchenPlaceOrder kitchenPlaceOrder1 = new KitchenPlaceOrder(
                            barItems.getCode(),
                            barItems.getName(),
                            barItems.getPrice(),
                            x,
                            Pbtn
                    );
                    kitchenCart.remove(number);
                    kitchenCart.add(kitchenPlaceOrder1);
                    Pbtn.setOnAction((event) -> {
                        kitchenCart.remove(kitchenPlaceOrder);
                        cartBar.remove(afterBarCartTm);
                    });
                }
                tblBarAddToCart.setItems(cartBar);
            });
            BarItemtbl.setItems(tabledata);
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


    public ObservableList<KitchenPlaceOrder> returnBarCart() {

        return PlaceBarCart;
    }


    private int isExists(AfterBarCartTm afterBarCartTm) {
        for (int i = 0; i < cartBar.size(); i++) {
            if (afterBarCartTm.getItemCode()
                    .equals(cartBar.get(i).getItemCode())) {
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

    public void CustomerInfoOnAction(ActionEvent actionEvent) throws IOException {
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


    public void PrintToBarOnAction(ActionEvent event) {

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Bar_Order.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Get all values from table*/
            ObservableList<AfterBarCartTm> items = tblBarAddToCart.getItems();
            /*Create a Bean Array Data Source and pass the table values to it*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(items.toArray()));

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

            lblBarOrderId.setText(new OrderController().getOrderId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //----------search--------------------

    public void search(String text) throws SQLException, ClassNotFoundException {


        List <BarItemsTm> barItemsTms = searchCustomer(text);
        ObservableList<KitchenItemsTm> tableData = FXCollections.observableArrayList();
        for (BarItemsTm barItemsTm : barItemsTms) {
            Button btn = new Button("Add To Cart");
            tableData.add(new KitchenItemsTm(
                    barItemsTm.getCode(),
                    barItemsTm.getName(),
                    barItemsTm.getCode(),
                    barItemsTm.getSize(),
                    barItemsTm.getPrice(),
                    btn


            ));

            //======================btn===========



            btn.setOnAction((e) -> {
                AfterBarCartTm afterBarCartTm = new AfterBarCartTm(
                        lblTime.getText(),
                        barItemsTm.getName(),
                        barItemsTm.getSize(),
                        1,
                        new PlaceOrderFormController().cmbDeliveryBoy(),
                        new PlaceOrderFormController().setLblOrderId(),
                        barItemsTm.getCode()
                );
                Button Pbtn = new Button("Remove");
                KitchenPlaceOrder kitchenPlaceOrder = new KitchenPlaceOrder(
                        barItemsTm.getCode(),
                        barItemsTm.getName(),
                        barItemsTm.getPrice(),
                        orderqty,
                        Pbtn
                );
                int number = isExists(afterBarCartTm);

                if (number == -1) {

                    cartBar.add(afterBarCartTm);
                    kitchenCart.add(kitchenPlaceOrder);

                    Pbtn.setOnAction((event) -> {
                        cartBar.remove(afterBarCartTm);
                        kitchenCart.remove(kitchenPlaceOrder);
                    });


                } else {
                    AfterBarCartTm temp = cartBar.get(number);
                    int x = temp.getOrderqty();


                    AfterBarCartTm newTm = new AfterBarCartTm(
                            lblTime.getText(),
                            temp.getName(),
                            temp.getSize(),
                            ++x,
                            new PlaceOrderFormController().cmbDeliveryBoy(),
                            new PlaceOrderFormController().setLblOrderId(),
                            temp.getItemCode()

                    );
                    cartBar.remove(number);
                    cartBar.add(newTm);


                    KitchenPlaceOrder kitchenPlaceOrder1 = new KitchenPlaceOrder(
                            barItemsTm.getCode(),
                            barItemsTm.getName(),
                            barItemsTm.getPrice(),
                            x,
                            Pbtn
                    );
                    kitchenCart.remove(number);
                    kitchenCart.add(kitchenPlaceOrder1);
                    Pbtn.setOnAction((event) -> {
                        kitchenCart.remove(kitchenPlaceOrder);
                        cartBar.remove(afterBarCartTm);
                    });
                }
                tblBarAddToCart.setItems(cartBar);
            });

        }

        BarItemtbl.getItems().setAll(tableData);
    }

    private List<BarItemsTm> searchCustomer(String value) throws SQLException, ClassNotFoundException {


        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM BarItems WHERE name  LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<BarItemsTm> barItemsTms= new ArrayList<>();

        Button btn=new Button("Add To Cart");

        while (rs.next()) {
            barItemsTms.add(new BarItemsTm(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    btn

            ));
        }

        return barItemsTms;


    }
}



