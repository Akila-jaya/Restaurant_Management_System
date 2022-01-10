package controller;

import db.DbConnection;
import javafx.collections.ObservableList;
import model.BarItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarItemsController implements BarItemsService {


    @Override
    public  boolean saveBarItems(BarItems c) throws SQLException, ClassNotFoundException {

        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO BarItems VALUES (?,?,?,?,?)";
        PreparedStatement stm= con.prepareStatement(query);
        stm.setObject(1,c.getCode());
        stm.setObject(2,c.getName());
        stm.setObject(3,c.getDesc());
        stm.setObject(4,c.getSize());
        stm.setObject(5,c.getPrice());


        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteBarItems(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM BarItems WHERE code='"+id+"'");
        return stm.executeUpdate()>0;


    }



    @Override
    public boolean updateBarItems(BarItems c) throws SQLException, ClassNotFoundException {
          PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE BarItems SET  name=?, description=?,size=?,price=? WHERE code=?");
               //
                stm.setObject(1,c.getName());
                stm.setObject(2,c.getDesc());
                stm.setObject(3,c.getSize());
                stm.setObject(4,c.getPrice());
                stm.setObject(5,c.getCode());

                return stm.executeUpdate()>0;
    }

    @Override
    public BarItems getBarItems(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= (PreparedStatement) DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM BarItems WHERE code =?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
if (rst.next()){
return new BarItems
        (rst.getString(1),
        rst.getString(2),
        rst.getString(3),
        rst.getString(4),
        rst.getInt(5)
                );

}else{
    return null;
}
    }




}
