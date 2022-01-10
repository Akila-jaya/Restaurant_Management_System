package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Tables;
import model.Worker;
import tm.BookTablesTm;
import tm.SetDeliveryBoyTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CheckTableController {

    public AnchorPane SetDeliveryAnchor;
    public TableView tblViewCheckTables;
    public TableColumn colTableId;
    public TableColumn colTableName;
    public TableColumn colOrderId;
    public TableColumn colNoOfChairs;
    public TableColumn colStatus;
    public TableColumn colToFree;
    private Object BookTablesTm;

    public void initialize(){

        try {
            GetBookedTables();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colTableId.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTableName.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNoOfChairs.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colToFree.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }





    public   void GetBookedTables() throws SQLException, ClassNotFoundException {
        ObservableList<BookTablesTm> bookTables= FXCollections.observableArrayList();

        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM customerTable WHERE status='busy'");
        List<Tables> tables= FXCollections.observableArrayList();
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
            Button btn=new Button("To Free");
            bookTables.add(new BookTablesTm(
                    tables1.getId(),
                    tables1.getName(),
                    tables1.getNoOfChairs(),
                    tables1.getStatus(),
                    btn
            ));

            btn.setOnAction((e) -> {
                try {
                    setToFree(tables1.getId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

                bookTables.remove(BookTablesTm);
                try {
                    GetBookedTables();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }


            });
        }

        tblViewCheckTables.setItems(bookTables);
    }



    public void setToFree(String id) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE  customerTable SET status='free' WHERE id=?");
        stm.setObject(1,id );
        if (stm.executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION,"OK UPDATE").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATE").show();
        }

    }


}




