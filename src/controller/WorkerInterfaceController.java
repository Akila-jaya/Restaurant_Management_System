package controller;

import db.DbConnection;
import model.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerInterfaceController implements WorkerService{
    @Override
    public boolean SaveWorker(Worker w) throws SQLException, ClassNotFoundException {

        Connection con=DbConnection.getInstance().getConnection();
        String query="INSERT INTO Worker VALUES(?,?,?,?,?,?)";

        PreparedStatement stm=con.prepareStatement(query);
        stm.setObject(1,w.getId());
        stm.setObject(2,w.getName());
        stm.setObject(3,w.getType());
        stm.setObject(4,w.getStatus());
        stm.setObject(5,w.getContact());
        stm.setObject(6,w.getEmail());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean DeleteWorker(String code) throws SQLException, ClassNotFoundException {

        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Worker WHERE id='"+code+"'");
        return stm.executeUpdate()>0;


        }



    @Override
    public boolean UpdateWorker(Worker w) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Worker SET name=?, type=?, status=?, contact=?, email=? WHERE id=?");

        stm.setObject(1,w.getName());
        stm.setObject(2,w.getType());
        stm.setObject(3,w.getStatus());
        stm.setObject(4,w.getContact());
        stm.setObject(5,w.getEmail());
        stm.setObject(6,w.getId());


        return  stm.executeUpdate()>0;
    }

    @Override
    public Worker getWorker(String code) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Worker WHERE id=?");
        stm.setObject(1,code);
        ResultSet resultSet=stm.executeQuery();
        if (resultSet.next()){
            return  new Worker(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }
}
