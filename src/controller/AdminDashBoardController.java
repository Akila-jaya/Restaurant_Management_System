package controller;

import animatefx.animation.FadeIn;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.AfterKitchenCartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AdminDashBoardController {
    public   Label lblDate;
    public Label lblTime;
    public AnchorPane AllDashboard;
    public StackPane panelRoot;
    public StackPane stackPane;

  /*  static String time=null;
    static String date=null;*/

    public void initialize(){
        loadDateAndTime();
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

   /* public String getTime(){
        time=lblTime.getText();
        return time;
    }
    public String getDate(){
        date=lblDate.getText();
        return date;
    }*/
    public void BarItemonAction(ActionEvent actionEvent) {
        try{
            AnchorPane pane;
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../views/Bar.fxml"));
            pane=fxmlLoader.load();
            AllDashboard.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new FadeIn(AllDashboard).play();

    }

    public void KitchenItemsOnAction(ActionEvent actionEvent) {
        try{
            AnchorPane pane;
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../views/Kitchen.fxml"));
            pane=fxmlLoader.load();
            AllDashboard.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
        new FadeIn(AllDashboard).play();


    }

    public void TablesOnAction(ActionEvent actionEvent) {
        try{
            AnchorPane pane;
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../views/Table.fxml"));
            pane=fxmlLoader.load();
            AllDashboard.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new FadeIn(AllDashboard).play();
    }

    public void MakeSalaryOnAction(ActionEvent actionEvent) {
        try{
            AnchorPane pane;
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../views/MakeSalary.fxml"));
            pane=fxmlLoader.load();
            AllDashboard.getChildren().setAll(pane);


        } catch (IOException e) {
            e.printStackTrace();
        }
        new FadeIn(AllDashboard).play();

    }

    public void EmployeeOnAction(ActionEvent actionEvent) {
        try{
            AnchorPane pane;
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../views/Employee.fxml"));
            pane=fxmlLoader.load();
            AllDashboard.getChildren().setAll(pane);


        } catch (IOException e) {
            e.printStackTrace();
        }
        new FadeIn(AllDashboard).play();

    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/Login.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) AllDashboard.getScene().getWindow();
        window.setScene(new Scene(load));

        new FadeIn(AllDashboard).play();
    }

    public void IncomeReportsOnAction(ActionEvent event) throws IOException {
        try{
            AnchorPane pane;
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("../views/Reports.fxml"));
            pane=fxmlLoader.load();
            AllDashboard.getChildren().setAll(pane);


        } catch (IOException e) {
            e.printStackTrace();
        }
        new FadeIn(AllDashboard).play();

    }

    public void btnBestSellingItems(ActionEvent event) {


        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Charts.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Here we can set the DBConnection to the data source because this report use mysql to get data*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }




    }

