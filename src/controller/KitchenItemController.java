package controller;

import db.DbConnection;
import model.KitchenItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KitchenItemController implements KitchenItemService {
    @Override
    public boolean saveKitchenItems(KitchenItems c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO KitchenItems VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, c.getCode());
        stm.setObject(2, c.getName());
        stm.setObject(3, c.getDesc());
        stm.setObject(4, c.getSize());
        stm.setObject(5, c.getPrice());

        return  stm.executeUpdate()> 0;
    }



        @Override
        public boolean updateKitchenItems(KitchenItems c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement(
                "UPDATE KitchenItems SET name=?,  description=?,  size=? ,price=? WHERE code=?"
        );
        stm.setObject(1,c.getName());
        stm.setObject(2,c.getDesc());
        stm.setObject(3,c.getSize());
        stm.setObject(4,c.getPrice());
        stm.setObject(5,c.getCode());


        return stm.executeUpdate()>0;

        }

        @Override
        public boolean deleteKitchenItems(String code) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement(
                "DELETE FROM KitchenItems WHERE code='"+code+"'"
        );

        return stm.executeUpdate()>0;
        }

        @Override
        public KitchenItems getKitchenItems(String c) throws SQLException, ClassNotFoundException {
            PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM KitchenItems WHERE code" +
                            "=?");
        stm.setObject(1,c);

            ResultSet rst=stm.executeQuery();

            if (rst.next()){
                return new KitchenItems(
                        rst.getString(1),
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
