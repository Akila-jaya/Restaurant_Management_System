package controller;

import db.DbConnection;
import model.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableInterfaceController implements TableService{
    @Override
    public boolean tableSave(Tables tables) throws SQLException, ClassNotFoundException {

        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO CustomerTable VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,tables.getId());
        stm.setObject(2,tables.getName());
        stm.setObject(3,tables.getOrderId());
        stm.setObject(4,tables.getStatus());
        stm.setObject(5,tables.getNoOfChairs());
        return  stm.executeUpdate()>0;
    }

    @Override
    public boolean tableUpdate(Tables t) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE CustomerTable SET name=?, orderId=?, status=?, noOfChairs=? WHERE id=?");
        stm.setObject(1,t.getName());
        stm.setObject(2,t.getOrderId());
        stm.setObject(3,t.getStatus());
        stm.setObject(4,t.getNoOfChairs());
        stm.setObject(5,t.getId());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean tableDelete(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM CustomerTable WHERE id='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Tables getTables(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM CustomerTable WHERE id=?");
        stm.setObject(1, id);
        ResultSet resultSet=stm.executeQuery();
        if (resultSet.next()){
            return new Tables(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }else{
            return null;

        }


    }

}
