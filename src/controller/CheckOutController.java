package controller;

import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.BarChartM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.CheckOutKitchenItems;
import tm.KitchenPlaceOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;

import static controller.KitchenOrderController.kitchenCart;
import static sun.management.Agent.getText;

public class CheckOutController {
    public AnchorPane CheckOut;
    public TableColumn colProductName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colTotal;
    public TableColumn colRemove;
    public TableView tblCheckOut;
    public Label lblBillTotal;
    public Label LblDeliveryCharge;

    public Label LblAmountTotal;
    public Label lblTime;
    public Label lblDate;


    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        getKitchenCart();
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("MM");
        lblDate.setText(f.format(date));
        System.out.println(lblDate.getText());

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

    static  ObservableList <CheckOutKitchenItems>chekOutKitchenCart=FXCollections.observableArrayList();

    public void getKitchenCart() throws SQLException, ClassNotFoundException {


        tblCheckOut.getItems().clear();

        for (KitchenPlaceOrder kitchenPlaceOrder : kitchenCart) {
             Button btn=new Button("Remove");

     chekOutKitchenCart .add(new CheckOutKitchenItems(
               kitchenPlaceOrder.getName(),
               kitchenPlaceOrder.getOrderQty(),
               kitchenPlaceOrder.getPrice(),
               kitchenPlaceOrder.getOrderQty()* kitchenPlaceOrder.getPrice(),
                btn
            ));

         }

        tblCheckOut .setItems(chekOutKitchenCart);
        calculateCost();
    }

   String type=new PlaceOrderFormController().returnOrderType();

    void calculateCost(){

        double ttl=0;
        double amontTtl=0;
        for (CheckOutKitchenItems tm:chekOutKitchenCart
        ) {
            ttl+=tm.getTotal();
        }
        lblBillTotal.setText(String.valueOf(ttl));
        if (type=="Delivery"){
            amontTtl=ttl+(ttl*0.1);
        }else {
            amontTtl=ttl;
        }
        LblAmountTotal.setText(String.valueOf(amontTtl));
    }

    public void checkOutOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/PrintCustomerBill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            ObservableList<CheckOutKitchenItems> items = tblCheckOut.getItems();



            HashMap map = new HashMap();
            Double billTotal= Double.valueOf(lblBillTotal.getText());
            String deliveryCharge=LblDeliveryCharge.getText();
            Double amountTotal= Double.valueOf(LblAmountTotal.getText());
            map.put("Bill_Total",billTotal);
            map.put("Delivery_Charge",deliveryCharge);
            map.put("Amount_Total",amountTotal);



            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,map,new JRBeanArrayDataSource(items.toArray()));

            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        }

        //==================jasper




        for (KitchenPlaceOrder kitchenPlaceOrder1 : kitchenCart) {

            Connection con = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO CheckOuts VALUES (?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, kitchenPlaceOrder1.getName());
            stm.setObject(2, kitchenPlaceOrder1.getOrderQty());
            stm.setObject(3, kitchenPlaceOrder1.getPrice());
            stm.setObject(4, kitchenPlaceOrder1.getOrderQty() * kitchenPlaceOrder1.getPrice());
            stm.setObject(5,lblDate.getText() );


            stm.executeUpdate();
        }

        //===================end jasper




    }








    public ObservableList<BarChartM> getIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT date,SUM(total) as total FROM CheckOuts GROUP BY date");
        ResultSet rst = stm.executeQuery();
        ObservableList<BarChartM> income = FXCollections.observableArrayList();
        while (rst.next()) {
            income.add(new BarChartM(
                    rst.getString(1),
                    rst.getInt(2)

            ));


        }
        return income;
    }
}


