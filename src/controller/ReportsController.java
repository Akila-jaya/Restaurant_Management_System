package controller;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.BarChartM;

import java.sql.SQLException;

public class ReportsController {
    public TableColumn colMonth;
    public TableColumn coltotal;
    public TableView tblMonthlyReports;
    public BarChart barChart;
    public CategoryAxis txtDate;

    public void initialize() {

        try {
            loadChart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadChart() throws SQLException, ClassNotFoundException {
        ObservableList<BarChartM> income=new CheckOutController().getIncome();
        XYChart.Series series=new XYChart.Series<>();

        for (BarChartM checkOuts:income) {

            series.getData().add(new XYChart.Data(checkOuts.getdate(),checkOuts.getTotal()));
        }
        barChart.getData().addAll(series);


    }

}
