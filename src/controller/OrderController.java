package controller;

import db.DbConnection;
import model.BarOrderDetail;
import model.KitchenOrderDetail;
import model.Order;
import tm.AfterBarCartTm;
import tm.AfterKitchenCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT id FROM `Order` ORDER BY id DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "O-00"+tempId;
            }else if(tempId<99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }









    public boolean placeOrder(Order order, BarOrderDetail barOrderDetail, KitchenOrderDetail kitchenOrderDetail) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO `Order` VALUES(?,?,?,?,?)");


            stm.setObject(1, order.getId());
            stm.setObject(2, order.getCustId());
            stm.setObject(3, order.getoType());
            stm.setObject(4, order.getoDate());
            stm.setObject(5, order.getoTime());

            if (stm.executeUpdate() > 0) {
                if (saveOrderDetail(order.getId(), order.getAfterKitchenCartTms())) {
                    if(saveBarOrderDetail(order.getId(),order.getAfterBarCartTms())){

                        con.commit();
                        return true;
                    }
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    private boolean saveBarOrderDetail(String id, ArrayList<AfterBarCartTm> afterBarCartTms) throws SQLException, ClassNotFoundException {

        for (AfterBarCartTm temp : afterBarCartTms
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO BarOrderDetail VALUES(?,?,?,?,?,?,?)");
            stm.setObject(1, temp.getOrderId());
            stm.setObject(2, temp.getOrderqty());
            stm.setObject(3, temp.getName());
            stm.setObject(4, temp.getItemCode());
            stm.setObject(5, temp.getSize());
            stm.setObject(6, new PlaceOrderFormController().lblTime);
            stm.setObject(7, temp.getWorkerId());
            if (stm.executeUpdate() > 0) {

               /* if (updateQty(temp.getItemCode(), temp.getQtyForSell())){

                }else{
                    return false;
                }

            } else {
                return false;
            }*/
            }
        }
        return true;
    }




    private boolean saveOrderDetail(String orderId, ArrayList<AfterKitchenCartTm> items) throws SQLException, ClassNotFoundException {
        for (AfterKitchenCartTm temp : items
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO KitchenOrderDetail VALUES(?,?,?,?,?,?,?)");
            stm.setObject(1, temp.getOrderId());
            stm.setObject(2, temp.getOrderQty());
            stm.setObject(3, temp.getItemName());
            stm.setObject(4, temp.getItemCode());
            stm.setObject(5, temp.getSize());
            stm.setObject(6, null);
            stm.setObject(7, temp.getWorkerId());
            if (stm.executeUpdate() > 0) {

               /* if (updateQty(temp.getItemCode(), temp.getQtyForSell())){

                }else{
                    return false;
                }

            } else {
                return false;
            }*/
            }
        }
        return true;
    }
}







